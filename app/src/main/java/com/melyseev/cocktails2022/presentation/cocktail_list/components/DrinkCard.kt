package com.melyseev.cocktails2022.presentation.cocktail_list.components

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.melyseev.cocktails2022.common.DEFAULT_DRINK_IMAGE
import com.melyseev.cocktails2022.common.loadPicture

import com.melyseev.cocktails2022.data.remote.dto.short_describe_cocktail.Drink

@Composable
fun DrinkCard(
    drink: Drink,
    onClick: () -> Unit,
    index: Int,
    //loadPicture: (urlParam: String, MutableState<Bitmap>) -> Unit,
    //loadDefaultPicture: () -> MutableState<Bitmap>
){
    Card(shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 6.dp, top = 6.dp
            )
            .fillMaxWidth()
            .clickable(onClick = onClick)
    )
    {

/*
        val bitmapState: MutableState<Bitmap> = remember {
            loadDefaultPicture()
        }
        loadPicture(drink.strDrinkThumb, bitmapState)
*/


        Column {


            drink.strDrinkThumb.let {
                val image = loadPicture(url = it, defaultImage = DEFAULT_DRINK_IMAGE).value
                image?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(255.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            drink.strDrink.let { title ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
                    ) {
                        Text(
                            text = title,
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .wrapContentWidth(Alignment.Start),
                            style = MaterialTheme.typography.h5
                        )
                        Text(
                            //text = recipe.rating.toString(),
                            text = index.toString(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.End)
                                .align(Alignment.CenterVertically),
                            style = MaterialTheme.typography.h6
                        )
                    }
                }
            }
        }

}