package com.melyseev.cocktails2022.data.repository

import com.melyseev.cocktails2022.data.remote.RetrofitService
import com.melyseev.cocktails2022.data.remote.dto.short_describe_cocktail.ShortDto
import com.melyseev.cocktails2022.domain.repository.CocktailRepositoryList
import javax.inject.Inject


class CocktailRepositoryListImpl @Inject constructor(val retrofitService: RetrofitService): CocktailRepositoryList {

    override suspend fun getCocktailListByCategory(category: String): ShortDto {
        return retrofitService.getCocktailListByCategories(category)
    }

    override suspend fun getCocktailListByAlcoholic(category: String): ShortDto {
        return retrofitService.getCocktailListByAlcoholic(category)
    }

    override suspend fun getCocktailListByGlasses(category: String): ShortDto {
        return retrofitService.getCocktailListByGlasses(category)
    }

    override suspend fun getCocktailListByIngredients(category: String): ShortDto {
        return retrofitService.getCocktailListByIngredients(category)
    }
}