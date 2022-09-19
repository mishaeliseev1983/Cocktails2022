package com.melyseev.cocktails2022.presentation.cocktail_list

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction1

@Composable
fun AppSearchBar(
    filterName: String,
    listCategories: List<String>?,
    selectedCategory: String,
    onSelectedValueCategoryChanged:(String)->Unit,
    onShowFilters:()->Unit,
    categoryValueScrollPosition: Int = -1,
) {

    val coroutineScope = rememberCoroutineScope()
    val stateScrollPosition = rememberLazyListState()

    Surface(

        elevation = 8.dp,
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.surface
    ) {

        Column(
            modifier = Modifier
                .padding(top = 20.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 10.dp, bottom = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = filterName,
                    //textAlign = Alignment.Center,
                    style = TextStyle(
                        color = MaterialTheme.colors.primary,
                        fontSize = 30.sp,
                    ),
                )

                IconButton(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    onClick = {
                        onShowFilters()
                    },

                    ) {

                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "",
                        tint = Color.Blue
                    )
                }
            }

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, bottom = 8.dp),
                state = stateScrollPosition
            ) {
                itemsIndexed(items = listCategories.orEmpty()) { index, categoryValue ->
                    DrinkCategoryBtn(
                        isSelected = selectedCategory == categoryValue,
                        category = categoryValue,
                        onSelectedValueCategoryChanged = {
                            onSelectedValueCategoryChanged(it)
                        })

                }
            }
        }
    }

    LaunchedEffect(key1 = categoryValueScrollPosition, block = {
        coroutineScope.launch {
            if (categoryValueScrollPosition != -1) {
                stateScrollPosition.scrollToItem(categoryValueScrollPosition)
            }
        }
    })
}
