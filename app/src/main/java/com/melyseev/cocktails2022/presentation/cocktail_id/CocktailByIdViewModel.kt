package com.melyseev.cocktails2022.presentation.cocktail_id

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melyseev.cocktails2022.common.Constants
import com.melyseev.cocktails2022.common.GenericState
import com.melyseev.cocktails2022.data.remote.dto.byid_describe_cocktail.ByIdDto
import com.melyseev.cocktails2022.domain.use_case.get_data.CocktailByIdUseCase
import com.melyseev.cocktails2022.domain.use_case.update_data.CocktailChangeLikeUseCase
import com.melyseev.cocktails2022.domain.use_case.get_data.CocktailGetLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CocktailByIdViewModel
@Inject constructor(
    private val cocktailByIdUseCase: CocktailByIdUseCase,
    val cocktailGetLikeUseCase: CocktailGetLikeUseCase,
    private val cocktailChangeLikeUseCase: CocktailChangeLikeUseCase,

    savedStateHandle: SavedStateHandle,
    ): ViewModel(){

    private var _stateByIdCocktail = mutableStateOf(CocktailByIdState())
    val stateByIdCocktail: State<CocktailByIdState> = _stateByIdCocktail

    private var _stateLikeCocktail = mutableStateOf(CocktailLikeState())
    val stateLikeCocktail: State<CocktailLikeState> = _stateLikeCocktail

        init {
            savedStateHandle.get<String>(Constants.PARAM_COCKTAIL_ID)?.let { it ->
                println("Init CocktailByIdViewModel - $it")
               cocktailByIdUseCase.execute(idCocktail =  it).onEach{ genericState ->
                    when (genericState) {
                        is GenericState.Success<ByIdDto> -> {

                            viewModelScope.launch {
                                genericState.data?.drinks?.get(0)?.let {
                                    /*
                                    cocktailDescription?.strDrinkThumb?.let { url ->
                                        loadImage(url, cocktailDescription)
                                    }
                                     */

                                    var  cocktailById =  CocktailByIdState(data = genericState.data)
                                    _stateByIdCocktail.value =
                                        cocktailById
                                }
                            }
                        }
                        is GenericState.Error<ByIdDto> -> {
                            _stateByIdCocktail.value = CocktailByIdState(error = genericState.error)
                        }
                        is GenericState.Loading<ByIdDto> -> {
                            _stateByIdCocktail.value = CocktailByIdState(loading = true)
                        }
                    }
                }.launchIn(viewModelScope)


                getLike(cocktailId = it.toLong())
            }
        }

    fun getLike(cocktailId: Long){
        cocktailGetLikeUseCase.execute(cocktailId = cocktailId).onEach {
        state ->
            when (state) {
                is GenericState.Success<CocktailLikeState> -> {
                    state.data?.let {
                        _stateLikeCocktail.value = it
                    }
                }
                is GenericState.Loading<CocktailLikeState> -> {
                    _stateLikeCocktail.value = CocktailLikeState(loading = true)
                }
                is GenericState.Error<CocktailLikeState> -> {
                    _stateLikeCocktail.value = CocktailLikeState(error = state.error)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun changeLike(cocktailId: Long, title: String, image: String){
        cocktailChangeLikeUseCase.execute(cocktailId, title, image).onEach {
            state ->
            when (state) {
                is GenericState.Success<CocktailLikeState> -> {
                    state.data?.let {
                        _stateLikeCocktail.value = it
                    }
                }
                is GenericState.Loading<CocktailLikeState> -> {
                    _stateLikeCocktail.value = CocktailLikeState(loading = true)
                }
                is GenericState.Error<CocktailLikeState> -> {
                    _stateLikeCocktail.value = CocktailLikeState(error = state.error)
                }
            }
        }.launchIn(viewModelScope)


        /*
        viewModelScope.launch {


            _stateByIdCocktail.value

            cocktailChangeLikeUseCase.execute(cocktailId.toLong())

            _stateByIdCocktail.value.copy()
            val color = cocktailGetLikeUseCase.execute(cocktailId.toLong())
            println("color = $color")
            val s = CocktailByIdState()
            s.like = stateByIdCocktail.value.like
            s.data = stateByIdCocktail.value.data
            s.error = stateByIdCocktail.value.error
            _stateByIdCocktail.value = CocktailByIdState(_stateByIdCocktail)
        }

         */
    }


}