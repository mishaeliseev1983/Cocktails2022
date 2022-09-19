package com.melyseev.cocktails2022.presentation.cocktail_list

import com.melyseev.cocktails2022.data.remote.dto.short_describe_cocktail.ShortDto

class CocktailListState(
    val error: String = "",
    var data:  ShortDto? = null,
    val loading: Boolean = false
)