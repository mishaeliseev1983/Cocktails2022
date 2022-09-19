package com.melyseev.cocktails2022.presentation.cocktail_filter_categories

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.melyseev.cocktails2022.R
import com.melyseev.cocktails2022.common.BottomButton
import com.melyseev.cocktails2022.presentation.Screen


@Composable
fun CocktailFilterCategoriesScreen(navController: NavController) {

    val viewModel = hiltViewModel<CocktailFilterCategoriesViewModel>()
    val stateCategories = viewModel.stateFilterCategoriesList.value

  //  Box(modifier = Modifier.fillMaxSize()) {


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {

        Row{
            Text(
                modifier = Modifier.weight(0.3f),
                text = viewModel.filterName,
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.primary,
                fontStyle = FontStyle.Normal,
                //modifier = Modifier.padding(bottom = 20.dp)
            )
        }

        stateCategories.data?.drinks?.let { drinks ->
            //Success
            LazyColumn(
                modifier = Modifier.weight(0.3f)
            ) {

                itemsIndexed(items = drinks) { index,

                   element ->
                    LabelledCheckBox(
                        checked = element.ischecked,
                        onCheckedChange = {
                            viewModel.updateFilterCategory(categoryIndex = index, !element.ischecked)
                        },
                        label = element.categoryName
                    )
                }
            }

            BottomButton(textButton = stringResource(id = R.string.back)) {
                    navController.navigate(Screen.CocktailFilters.route) {
                        popUpTo(navController.graph.id) {
                            saveState = true
                        }
                    }
                }

        }


        //Error
        if (stateCategories.error.isNotEmpty()) {
            Text(text = stateCategories.error)
        }
        if (stateCategories.loading) {
            CircularProgressIndicator()
        }
    }

}