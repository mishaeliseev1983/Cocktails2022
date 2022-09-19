package com.melyseev.cocktails2022.domain.use_case.get_data

import com.melyseev.cocktails2022.common.GenericState
import com.melyseev.cocktails2022.data.mapper.MapperLikeEntity
import com.melyseev.cocktails2022.data.remote.dto.short_describe_cocktail.ShortDto
import com.melyseev.cocktails2022.domain.repository.CocktailRepositoryDataBase
import com.melyseev.cocktails2022.domain.repository.CocktailRepositoryList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class CocktailListUseCase
@Inject constructor(
    val repository: CocktailRepositoryList,
    val repositoryDBForFavorite: CocktailRepositoryDataBase) {

    //add repo db
    //get favorite
    fun execute(filterName: String, category: String): Flow<GenericState<ShortDto>> = flow{
        emit(GenericState.Loading<ShortDto>())
        try {
            var  shortDto = ShortDto(listOf())
            when(filterName.lowercase().first()){
                'f'->{
                    shortDto = MapperLikeEntity.fromListLikeEntityToShortDto( repositoryDBForFavorite.getListFavorite())
                }
                'a'->{
                    shortDto = repository.getCocktailListByAlcoholic( category = category)
                }
                'c'->{
                    shortDto = repository.getCocktailListByCategory( category = category)
                }
                'g'->{
                    shortDto = repository.getCocktailListByGlasses( category = category)
                }
                'i'->{
                    shortDto = repository.getCocktailListByIngredients( category = category)
                }
            }
            emit(GenericState.Success(data = shortDto))
        }catch (e:Exception){
            emit(GenericState.Error<ShortDto>( error = e.localizedMessage))
        }
    }
}
