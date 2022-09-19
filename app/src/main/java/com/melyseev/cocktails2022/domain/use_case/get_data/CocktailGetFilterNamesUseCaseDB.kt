package com.melyseev.cocktails2022.domain.use_case.get_data

import com.melyseev.cocktails2022.domain.repository.CocktailRepositoryDataBase
import javax.inject.Inject

class CocktailGetFilterNamesUseCaseDB @Inject constructor(private val repository: CocktailRepositoryDataBase) {
    suspend fun execute(): List<String> {
        return repository.getFilterNames()
    }
}