package com.melyseev.cocktails2022.domain.use_case.get_data

import com.melyseev.cocktails2022.common.GenericState
import com.melyseev.cocktails2022.data.database.entity.FilterCategoryEntity
import com.melyseev.cocktails2022.data.remote.dto.list_categories.common.CommonName
import com.melyseev.cocktails2022.data.remote.dto.list_categories.common.ListCommonDto
import com.melyseev.cocktails2022.domain.repository.CocktailRepositoryDataBase
import com.melyseev.cocktails2022.domain.repository.CocktailRepositoryFiltersFromInternet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class CocktailGetFilterCheckedCategoriesDBInternetUseCase @Inject constructor(
    private val repositoryFromInet: CocktailRepositoryFiltersFromInternet,
    private val repositoryFromDB: CocktailRepositoryDataBase
    ) {

    fun execute(filterName: String): Flow<GenericState<ListCommonDto>> = flow {
        emit(GenericState.Loading<ListCommonDto>())


        //get categories from internet
        var listCategoriesAllByFilter = listOf<FilterCategoryEntity>()
        //1 try get from db
        listCategoriesAllByFilter =
                repositoryFromDB.getCategoriesAllByFilterName(filterName = filterName)


        // if db has not data -> go to internet
        if (listCategoriesAllByFilter.isEmpty()) {
             try {
                val categories =
                    repositoryFromInet.getCocktailListCategoriesByParam(param = filterName)

                println(" get categories : filterName = $filterName, categories = $categories" )
                val listCategory = categories.drinks.map { it.categoryName }
                repositoryFromDB.fillTableFilter()
                println(" fill db : filterName = $filterName, categories = $categories" )

                repositoryFromDB.fillTableFilterCategories(
                    filterName = filterName,
                    categories = listCategory
                )

            } catch (e: Exception) {
                emit(GenericState.Error<ListCommonDto>(error = e.localizedMessage))
                return@flow
            }
        }


            //get only checked categories from database
            try {
                val listFilterCategoryEntity =
                    repositoryFromDB.getCategoriesCheckedByFilterName(filterName = filterName)

                val resultListCategories: List<CommonName> =
                listFilterCategoryEntity.map { CommonName(it.categoryName, ischecked = it.categoryChecked) }

                emit(GenericState.Success(data = ListCommonDto(resultListCategories)))
            } catch (e: Exception) {
                emit(GenericState.Error<ListCommonDto>(error = e.localizedMessage))
            }
        }
}
