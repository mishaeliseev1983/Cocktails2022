package com.melyseev.cocktails2022.presentation.cocktail_list


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.melyseev.cocktails2022.R
import com.melyseev.cocktails2022.common.BottomButton
import com.melyseev.cocktails2022.common.CheckHearts
import com.melyseev.cocktails2022.presentation.Screen
import com.melyseev.cocktails2022.presentation.cocktail_list.components.DrinkCard

@Composable
fun CocktailListScreen(navController: NavController) {

    val viewModel = hiltViewModel<CocktailListViewModel>()
    val stateDrinks = viewModel.stateListDrinks.value
    val stateCategories = viewModel.stateListFilterCategories.value

    Box(modifier = Modifier.fillMaxSize()) {
        val scaffoldState = rememberScaffoldState()
        Scaffold(

            bottomBar = {

            },
            topBar = {

                if (stateCategories.loading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                stateCategories.data?.drinks?.map { it.categoryName }.let {
                    AppSearchBar(
                        filterName = viewModel.selectedFilter,
                        listCategories = it,
                        selectedCategory = viewModel.selectedCategoryByFilter,
                        onSelectedValueCategoryChanged = viewModel::onSelectedValueCategoryChanged,
                        onShowFilters = {
                            navController.navigate(Screen.CocktailFilters.route) {
                                popUpTo(navController.graph.id) {
                                    saveState = true
                                }
                            }
                        },
                        categoryValueScrollPosition = viewModel.indexSelectedFilterCategory
                    )
                }
            },
            scaffoldState = scaffoldState
        )
        {



            stateDrinks.data?.drinks?.let { drinks ->
                //Success
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(5.dp)
                ) {
                    val sortedDrinks = drinks.sortedBy { it.idDrink }
                    itemsIndexed(items = sortedDrinks) { index,
                                                         element ->

                        DrinkCard(
                            drink = element,
                            onClick = {
                                navController.navigate(Screen.CocktailId.route + "/${element.idDrink}") {
                                    /*popUpTo(navController.graph.id) {
                                        saveState = true
                                    }*/
                                }
                            },
                            index = index,
                        )
                    }

                    item {
                        CheckHearts{
                            sortedDrinks.isEmpty() && viewModel.selectedFilter.startsWith(
                                    prefix = "f",
                                    ignoreCase = true
                            )
                        }
                    }

                    item {
                        BottomButton(textButton = stringResource(id = R.string.change_filter)) {
                            navController.navigate(Screen.CocktailFilters.route) {
                                popUpTo(navController.graph.id) {
                                    saveState = true
                                }
                            }
                        }
                    }
                }
            }
        }

        //Error
        if (stateDrinks.error.isNotEmpty()) {
            Text(modifier = Modifier
                .padding(20.dp)
                .align(Alignment.Center), text = stateDrinks.error)
        }
        if (stateDrinks.loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }


    LaunchedEffect(key1 = viewModel, block = {
    })

}