package com.melyseev.cocktails2022.domain.repository

import com.melyseev.cocktails2022.data.remote.dto.byid_describe_cocktail.ByIdDto

interface CocktailRepositoryById {
    suspend fun getCocktailById(id: String): ByIdDto
}