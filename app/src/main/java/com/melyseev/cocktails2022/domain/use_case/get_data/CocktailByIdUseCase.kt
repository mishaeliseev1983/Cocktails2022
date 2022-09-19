package com.melyseev.cocktails2022.domain.use_case.get_data

import com.melyseev.cocktails2022.common.GenericState
import com.melyseev.cocktails2022.data.remote.dto.byid_describe_cocktail.ByIdDto
import com.melyseev.cocktails2022.data.remote.dto.short_describe_cocktail.ShortDto
import com.melyseev.cocktails2022.domain.repository.CocktailRepositoryById
import com.melyseev.cocktails2022.domain.repository.CocktailRepositoryList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class CocktailByIdUseCase @Inject constructor(val repository: CocktailRepositoryById)  {

    fun execute(idCocktail: String): Flow<GenericState<ByIdDto>> = flow{
        emit(GenericState.Loading<ByIdDto>())
        try {
            val  byIdDto = repository.getCocktailById(id = idCocktail)
            emit(GenericState.Success(data = byIdDto))
        }catch (e: Exception){
            emit(GenericState.Error<ByIdDto>( error = e.localizedMessage))
        }
    }
}