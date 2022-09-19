package com.melyseev.cocktails2022.presentation.cocktail_filter_categories

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melyseev.cocktails2022.common.Constants
import com.melyseev.cocktails2022.common.GenericState
import com.melyseev.cocktails2022.data.remote.dto.list_categories.common.ListCommonDto
import com.melyseev.cocktails2022.domain.use_case.get_data.CocktailGetFilterAllCategoriesDBUseCase
import com.melyseev.cocktails2022.domain.use_case.update_data.CocktailUpdateFilterCategoryDBUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailFilterCategoriesViewModel
@Inject constructor(
    private val useCaseGetFilterAllCategoriesDBUseCase: CocktailGetFilterAllCategoriesDBUseCase,
    private val useCaseUpdateFilterCategoryDBUseCase: CocktailUpdateFilterCategoryDBUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    var filterName = "Loading ..."
    private var _stateFilterCategoriesList = mutableStateOf( CocktailFilterCategoriesListState() )
    val stateFilterCategoriesList: State<CocktailFilterCategoriesListState> = _stateFilterCategoriesList

    init {
        savedStateHandle.get<String>(Constants.PARAM_CATEGORY_NAME)?.let {
            filterName = it
            useCaseGetFilterAllCategoriesDBUseCase.execute(it).onEach {

            genericState ->
                when (genericState) {
                    is GenericState.Success<ListCommonDto> -> {
                        _stateFilterCategoriesList.value =
                            CocktailFilterCategoriesListState(data = genericState.data)
                    }
                    is GenericState.Error<ListCommonDto> -> {
                        _stateFilterCategoriesList.value =
                            CocktailFilterCategoriesListState(error = genericState.error)
                    }
                    is GenericState.Loading<ListCommonDto> -> {
                        _stateFilterCategoriesList.value =
                            CocktailFilterCategoriesListState(loading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }


    fun updateFilterCategory(categoryIndex: Int, isChecked: Boolean) {

        viewModelScope.launch {
            stateFilterCategoriesList.value.data?.let {
                val selectCategory = it?.drinks?.get(categoryIndex)
                //update db
                if (useCaseUpdateFilterCategoryDBUseCase.execute(
                        filterName = filterName,
                        filterCategoryName = selectCategory.categoryName,
                        isChecked = isChecked
                    )
                ) {
                    //update list categories
                    selectCategory.ischecked = isChecked
                    _stateFilterCategoriesList.value =
                        CocktailFilterCategoriesListState(data = it)
                }
            }
        }
    }
}