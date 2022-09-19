package com.melyseev.cocktails2022.domain.repository

import com.melyseev.cocktails2022.data.remote.dto.list_categories.common.ListCommonDto


interface CocktailRepositoryFiltersFromInternet {
    suspend fun getCocktailListCategoriesByParam(param: String): ListCommonDto
}