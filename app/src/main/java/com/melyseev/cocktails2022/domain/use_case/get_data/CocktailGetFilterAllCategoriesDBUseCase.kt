package com.melyseev.cocktails2022.domain.use_case.get_data

import com.melyseev.cocktails2022.common.GenericState
import com.melyseev.cocktails2022.data.remote.dto.list_categories.common.CommonName
import com.melyseev.cocktails2022.data.remote.dto.list_categories.common.ListCommonDto
import com.melyseev.cocktails2022.domain.repository.CocktailRepositoryDataBase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CocktailGetFilterAllCategoriesDBUseCase @Inject constructor(
    private val repositoryFromDB: CocktailRepositoryDataBase
) {
    fun execute(filterName: String): Flow<GenericState<ListCommonDto>> = flow {
        emit(GenericState.Loading<ListCommonDto>())

        //get categories from database
        try {
            val listFilterCategoryEntity =
                repositoryFromDB.getCategoriesAllByFilterName(filterName = filterName)

            val resultListCategories: List<CommonName> =
                //    listFilterCategoryEntity.filter { it.categoryChecked }
                listFilterCategoryEntity.map { CommonName(it.categoryName, ischecked = it.categoryChecked) }

            emit(GenericState.Success(data = ListCommonDto(resultListCategories)))
        } catch (e: Exception) {
            emit(GenericState.Error<ListCommonDto>(error = e.localizedMessage))
        }
    }
}