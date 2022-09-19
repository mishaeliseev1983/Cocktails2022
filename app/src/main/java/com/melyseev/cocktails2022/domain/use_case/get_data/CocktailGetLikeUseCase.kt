package com.melyseev.cocktails2022.domain.use_case.get_data

import com.melyseev.cocktails2022.common.GenericState
import com.melyseev.cocktails2022.domain.repository.CocktailRepositoryDataBase
import com.melyseev.cocktails2022.presentation.cocktail_id.CocktailLikeState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class CocktailGetLikeUseCase @Inject constructor(
    private val repository: CocktailRepositoryDataBase
) {
    fun execute(cocktailId: Long): Flow<GenericState<CocktailLikeState>> = flow {
        emit(GenericState.Loading<CocktailLikeState>())
        try {
            val like = repository.getLike(cocktailId = cocktailId)
            val likeState = CocktailLikeState(data = like)
            emit(GenericState.Success<CocktailLikeState>(data = likeState))
        } catch (e: Exception) {
            emit(GenericState.Error<CocktailLikeState>(error = e.localizedMessage))
        }
    }
}