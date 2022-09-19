package com.melyseev.cocktails2022.presentation

sealed class Screen(val route: String) {

    object CocktailId: Screen("id")
    object CocktailList: Screen("list")
    object CocktailFilters: Screen("filter")
    object CocktailFilterCategories: Screen("filtercategories")
}
