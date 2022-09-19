package com.melyseev.cocktails2022.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.melyseev.cocktails2022.presentation.cocktail_filter_categories.CocktailFilterCategoriesScreen
import com.melyseev.cocktails2022.presentation.cocktail_filters.CocktailFiltersScreen
import com.melyseev.cocktails2022.presentation.cocktail_id.CocktailByIdScreen
import com.melyseev.cocktails2022.presentation.cocktail_list.CocktailListScreen
import com.melyseev.cocktails2022.presentation.ui.theme.Cocktails2022Theme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            Cocktails2022Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.CocktailList.route
                    ) {
                        composable(
                            route = Screen.CocktailList.route
                        ) {
                            CocktailListScreen(navController = navController)
                        }

                        composable(
                            route = Screen.CocktailId.route + "/{cockailId}"
                        ) {
                            CocktailByIdScreen(navController = navController)
                        }

                        composable(
                            route = Screen.CocktailFilters.route
                        ) {
                            CocktailFiltersScreen(navController = navController)
                        }

                        composable(
                            route = Screen.CocktailFilterCategories.route + "/{categoryName}"
                        ) {
                            CocktailFilterCategoriesScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}