package com.melyseev.cocktails2022.presentation.cocktail_id

data class CocktailLikeState(
    var error: String = "",
    var data:  Boolean? = null,
    var loading: Boolean = false,
)