package com.melyseev.cocktails2022.data.repository

import com.melyseev.cocktails2022.data.remote.RetrofitService
import com.melyseev.cocktails2022.data.remote.dto.byid_describe_cocktail.ByIdDto
import com.melyseev.cocktails2022.domain.repository.CocktailRepositoryById
import javax.inject.Inject

class CocktailRepositoryByIdImpl @Inject constructor(val retrofitService: RetrofitService): CocktailRepositoryById {
    override suspend fun getCocktailById(id: String): ByIdDto {
        return retrofitService.getCocktailById(id)
    }
}