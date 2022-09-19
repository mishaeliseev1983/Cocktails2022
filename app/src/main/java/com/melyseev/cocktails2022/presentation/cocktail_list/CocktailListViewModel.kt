package com.melyseev.cocktails2022.presentation.cocktail_list


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melyseev.cocktails2022.common.Constants
import com.melyseev.cocktails2022.common.GenericState
import com.melyseev.cocktails2022.data.datasharedpreferences.DataSharedPreferencesFilterName
import com.melyseev.cocktails2022.data.remote.dto.list_categories.common.ListCommonDto
import com.melyseev.cocktails2022.data.remote.dto.short_describe_cocktail.ShortDto
import com.melyseev.cocktails2022.domain.repository.CocktailRepositorySharedPreferences
import com.melyseev.cocktails2022.domain.use_case.get_data.CocktailGetFilterCheckedCategoriesDBInternetUseCase
import com.melyseev.cocktails2022.domain.use_case.get_data.CocktailListUseCase
import com.melyseev.cocktails2022.presentation.cocktail_filter_categories.CocktailFilterCategoriesListState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CocktailListViewModel
    @Inject constructor(
        private val cocktailListUseCase: CocktailListUseCase,
        private val cocktailGetFilterCategoriesUseCaseDBInternet: CocktailGetFilterCheckedCategoriesDBInternetUseCase,
        private val repositorySharedPreferences: CocktailRepositorySharedPreferences,
        ): ViewModel(){


    private var _stateListDrinks = mutableStateOf(CocktailListState())
    val stateListDrinks: State<CocktailListState> = _stateListDrinks

    var selectedFilter: String = "Loading ..."
    var selectedCategoryByFilter: String =""

    private var _stateListFilterCategories = mutableStateOf(CocktailFilterCategoriesListState())
    val stateListFilterCategories: State<CocktailFilterCategoriesListState> = _stateListFilterCategories

    var indexSelectedFilterCategory = -1

    init{
        viewModelScope.launch {

            val filterSharedPreferences = repositorySharedPreferences.getFilter()
            selectedCategoryByFilter  = filterSharedPreferences.filterCategoryName
            selectedFilter = filterSharedPreferences.filterName


            getFilterCategories()
            getCocktails()
        }
    }

    private fun getFilterCategories(){
        cocktailGetFilterCategoriesUseCaseDBInternet.execute( filterName = selectedFilter)
            .onEach { genericState ->
            when (genericState) {
                is GenericState.Success<ListCommonDto> -> {

                    _stateListFilterCategories.value = CocktailFilterCategoriesListState(data = genericState.data)
                    genericState.data?.drinks?.forEachIndexed { index, element ->
                        if(element.categoryName == selectedCategoryByFilter)
                            indexSelectedFilterCategory = index
                    }
                }
                is GenericState.Error<ListCommonDto> -> {
                    _stateListFilterCategories.value = CocktailFilterCategoriesListState(error = genericState.error)
                }
                is GenericState.Loading<ListCommonDto> -> {
                    _stateListFilterCategories.value = CocktailFilterCategoriesListState(loading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getCocktails(){

       cocktailListUseCase.execute( filterName = selectedFilter, category = selectedCategoryByFilter)
            .onEach { genericState ->
                when (genericState) {
                    is GenericState.Success<ShortDto> -> {
                        _stateListDrinks.value = CocktailListState(data = genericState.data)
                    }
                    is GenericState.Error<ShortDto> -> {
                        _stateListDrinks.value = CocktailListState(error = genericState.error)
                    }
                    is GenericState.Loading<ShortDto> -> {
                        _stateListDrinks.value = CocktailListState(loading = true)
                    }
                }
            }.launchIn(viewModelScope)
    }


/*
    fun loadImage2(url: String, stateBitmap: MutableState<Bitmap>) {
        viewModelScope.launch {
            repositoryUploadImage.uploadImage(
                srcImage = url,
                stateBitmap
            )
        }
    }

    fun loadDefaultImage2(): MutableState<Bitmap> =
        repositoryUploadImage.uploadDefaultImage(
            repositorySharedPreferences.getContext()
        )
*/

    fun onSelectedValueCategoryChanged(newValueListCategory: String){

        println("  = onSelectedValueCategoryChanged= $newValueListCategory" )
        selectedCategoryByFilter = newValueListCategory
        getCocktails()
        saveFilterCategoryToLocalFile(selectedCategoryByFilter)
    }

    private fun saveFilterCategoryToLocalFile(categoryByFilter: String) {
        val dataSharedPreferencesFilterName = DataSharedPreferencesFilterName( selectedFilter, categoryByFilter)
        repositorySharedPreferences.setFilter(dataSharedPreferencesFilterName)
    }

}