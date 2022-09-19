package com.melyseev.cocktails2022.presentation.cocktail_filter_categories

import com.melyseev.cocktails2022.data.remote.dto.list_categories.common.ListCommonDto

class CocktailFilterCategoriesListState(
    val error: String = "",
    var data:  ListCommonDto? = null,
    val loading: Boolean = false)