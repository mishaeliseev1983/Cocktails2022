package com.melyseev.cocktails2022.presentation.cocktail_filters

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melyseev.cocktails2022.common.GenericState
import com.melyseev.cocktails2022.data.datasharedpreferences.DataSharedPreferencesFilterName
import com.melyseev.cocktails2022.data.remote.dto.list_categories.common.ListCommonDto
import com.melyseev.cocktails2022.domain.repository.CocktailRepositorySharedPreferences
import com.melyseev.cocktails2022.domain.use_case.get_data.CocktailGetFilterCheckedCategoriesDBInternetUseCase
import com.melyseev.cocktails2022.domain.use_case.get_data.CocktailGetFilterNamesUseCaseDB
import com.melyseev.cocktails2022.presentation.cocktail_filter_categories.CocktailFilterCategoriesListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailFiltersViewModel  @Inject constructor(

    private val useCaseCocktailGetFilterNamesUseCaseDB: CocktailGetFilterNamesUseCaseDB,
    private val useCaseGetFilterCheckedCategoriesDBInternetUseCase: CocktailGetFilterCheckedCategoriesDBInternetUseCase,
    private val repositorySharedPreferences: CocktailRepositorySharedPreferences,
): ViewModel(){

    var selectedFilter= mutableStateOf("Loading ...")
    var selectedFilterCategory: String? = null

    private var _listFilters = mutableStateOf(listOf<String>())
    val listFilters: State<List<String>> = _listFilters


    private var _stateListFilterCategories = mutableStateOf(CocktailFilterCategoriesListState())
    val stateListFilterCategories: State<CocktailFilterCategoriesListState> = _stateListFilterCategories


    private fun getFilterNames() {
        viewModelScope.launch {
            _listFilters.value = useCaseCocktailGetFilterNamesUseCaseDB.execute()
        }
    }


    fun setSelectedFilterName(filterName: String){
        useCaseGetFilterCheckedCategoriesDBInternetUseCase.execute(filterName = filterName).onEach {
                genericState ->
                    when (genericState) {
                        is GenericState.Success<ListCommonDto> -> {

                            genericState.data?.drinks?.let {

                                    cocktails ->


                                if (cocktails.isNotEmpty()) {
                                    val findElement = cocktails.find {
                                        it.categoryName == selectedFilterCategory
                                    }

                                    if (findElement == null)
                                        cocktails.first {
                                            it.ischecked
                                        }?.let { notNullCategoryName ->
                                            val dataSharedPreferencesFilterName =
                                                DataSharedPreferencesFilterName(
                                                    filterName = filterName,
                                                    filterCategoryName = notNullCategoryName.categoryName
                                                )
                                            repositorySharedPreferences.setFilter(
                                                dataSharedPreferencesFilterName = dataSharedPreferencesFilterName
                                            )
                                        }
                                }else
                                {
                                    val dataSharedPreferencesFilterName =
                                        DataSharedPreferencesFilterName(
                                            filterName = filterName,
                                            filterCategoryName =""
                                        )
                                    repositorySharedPreferences.setFilter(
                                        dataSharedPreferencesFilterName = dataSharedPreferencesFilterName)
                                }
                            }

                            _stateListFilterCategories.value =
                                  CocktailFilterCategoriesListState(loading = false)


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



    init {
        getSelectedFilterName()
        getFilterNames()
    }

    private fun getSelectedFilterName(){
        val dataSharedPreferencesFilterName = repositorySharedPreferences.getFilter()
        selectedFilter.value = dataSharedPreferencesFilterName.filterName
        selectedFilterCategory = dataSharedPreferencesFilterName.filterCategoryName

        setSelectedFilterName( filterName = selectedFilter.value)
    }
}