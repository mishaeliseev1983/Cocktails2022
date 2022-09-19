package com.melyseev.cocktails2022.domain.repository

import com.melyseev.cocktails2022.data.remote.dto.short_describe_cocktail.ShortDto

interface CocktailRepositoryList{
   suspend fun getCocktailListByCategory(category: String): ShortDto
   suspend fun getCocktailListByAlcoholic(category: String): ShortDto
   suspend fun getCocktailListByGlasses(category: String): ShortDto
   suspend fun getCocktailListByIngredients(category: String): ShortDto
}