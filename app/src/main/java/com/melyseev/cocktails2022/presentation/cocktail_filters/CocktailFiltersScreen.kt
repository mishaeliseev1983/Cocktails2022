package com.melyseev.cocktails2022.presentation.cocktail_filters


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.melyseev.cocktails2022.R
import com.melyseev.cocktails2022.common.BottomButton
import com.melyseev.cocktails2022.presentation.Screen
import com.melyseev.cocktails2022.presentation.cocktail_filter_categories.LabelledCheckBox

@Composable
fun CocktailFiltersScreen(navController: NavController) {

    val viewModel = hiltViewModel<CocktailFiltersViewModel>()
    val listFilters = viewModel.listFilters.value

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(20.dp)
        ) {
            item {
                Text(
                    text = stringResource(id = R.string.choose_filter),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.primary,
                    fontStyle = FontStyle.Normal,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
            }

            listFilters.forEach { one_filter ->
                item {


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        //.align(Alignment.TopCenter),
                        horizontalArrangement = Arrangement.SpaceBetween

                    ) {
                        LabelledCheckBox(
                            checked = viewModel.selectedFilter.value == one_filter,
                            onCheckedChange = {
                                viewModel.selectedFilter.value = one_filter
                                viewModel.setSelectedFilterName(one_filter)
                            },
                            label = one_filter,
                            spacer = 20.dp,
                            textFontSize = 25.sp
                        )


                        if(needShowButton(one_filter)) {
                            Button(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically),
                                //.border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(12.dp))
                                //.size(35.dp),
                                //.padding(5.dp),
                                enabled = viewModel.selectedFilter.value == one_filter,
                                onClick = {

                                    val firstLetter =
                                        viewModel.selectedFilter.value
                                    navController.navigate(Screen.CocktailFilterCategories.route + "/${firstLetter}")
                                }
                            ) {
                                Icon(

                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = stringResource(id = R.string.select_filter_category)
                                )
                            }
                        }
                    }

                }
            }
            item {
                BottomButton(textButton = stringResource(id = R.string.back)) {
                    navController.navigate(Screen.CocktailList.route) {
                        popUpTo(navController.graph.id) {
                            saveState = true
                        }
                    }
                }
            }

            item {
                //Error
                if (viewModel.stateListFilterCategories.value.error.isNotEmpty())
                    Text(
                        modifier = Modifier
                            .padding(20.dp)
                            .align(Alignment.Center),
                        text = viewModel.stateListFilterCategories.value.error
                    )
                if (viewModel.stateListFilterCategories.value.loading) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

        }
    }
}

fun needShowButton(value: String)=
    !value.lowercase().startsWith("f")


