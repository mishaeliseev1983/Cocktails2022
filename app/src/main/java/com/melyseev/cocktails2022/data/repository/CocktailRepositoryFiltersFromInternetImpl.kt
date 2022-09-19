package com.melyseev.cocktails2022.data.repository

import com.melyseev.cocktails2022.common.Constants
import com.melyseev.cocktails2022.data.mapper.MapperCategoryName
import com.melyseev.cocktails2022.data.remote.RetrofitService
import com.melyseev.cocktails2022.data.remote.dto.list_categories.common.ListCommonDto
import com.melyseev.cocktails2022.domain.repository.CocktailRepositoryFiltersFromInternet
import javax.inject.Inject

class CocktailRepositoryFiltersFromInternetImpl @Inject constructor(
    val retrofitService: RetrofitService,
    private val mapperCategoryName: MapperCategoryName): CocktailRepositoryFiltersFromInternet {

    override suspend fun getCocktailListCategoriesByParam(param: String): ListCommonDto {
        when(param.first()){
            Constants.FILTER_ALCOHOLIC.first() ->
                return mapperCategoryName.fromAlcogolicDtoToCommonDto(retrofitService.getAllListCategoriesByAlcoholic())
            Constants.FILTER_GLASS.first() ->
                return mapperCategoryName.fromGlassDtoToCommonDto(retrofitService.getAllListCategoriesByGlasses())
            Constants.FILTER_INGREDIENTS.first() ->
                return mapperCategoryName.fromIngredientDtoToCommonDto(retrofitService.getAllListCategoriesByIngredients())
            Constants.FILTER_CATEGORY.first() ->
                return mapperCategoryName.fromCategoryDtoToCommonDto(retrofitService.getAllListCategoriesByCategories())
        }
        return ListCommonDto(emptyList())
    }
}