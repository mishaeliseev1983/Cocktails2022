package com.melyseev.cocktails2022.presentation.cocktail_id

import com.melyseev.cocktails2022.data.remote.dto.byid_describe_cocktail.ByIdDto

data class CocktailByIdState(
    var error: String = "",
    var data:  ByIdDto? = null,
    var loading: Boolean = false,
)