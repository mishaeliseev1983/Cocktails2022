package com.melyseev.cocktails2022.presentation.cocktail_id

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.melyseev.cocktails2022.R
import com.melyseev.cocktails2022.common.BottomButton
import com.melyseev.cocktails2022.common.Constants.DEFAULT_DRINK_IMAGE
import com.melyseev.cocktails2022.common.Constants.IMAGE_HEIGHT
import com.melyseev.cocktails2022.common.loadPicture
import com.melyseev.cocktails2022.data.remote.dto.byid_describe_cocktail.ByIdDto
import com.melyseev.cocktails2022.presentation.Screen
import kotlinx.coroutines.launch

@Composable
fun CocktailByIdScreen(navController: NavController) {

    val viewModel = hiltViewModel<CocktailByIdViewModel>()
    val stateById = viewModel.stateByIdCocktail.value
    val stateLike = viewModel.stateLikeCocktail.value



    stateById?.data.let {

        it?.drinks?.get(0)?.let { drinkFull ->
             LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {

                val drinkTitle = drinkFull.strDrink ?: "no title"
                val instructions = drinkFull.strInstructions ?: "no instructions"
                val idCocktail = drinkFull.idDrink
                val drinkImage = drinkFull.strDrinkThumb


                val listIngredients = mutableListOf<String>()
                listIngredients.add(drinkFull.strIngredient1 ?: "")
                listIngredients.add(drinkFull.strIngredient2 ?: "")
                listIngredients.add(drinkFull.strIngredient3 ?: "")
                listIngredients.add(drinkFull.strIngredient4 ?: "")
                listIngredients.add(drinkFull.strIngredient5 ?: "")
                listIngredients.add(drinkFull.strIngredient6 ?: "")

                listIngredients.add(drinkFull.strIngredient7 ?: "")
                listIngredients.add(drinkFull.strIngredient8 ?: "")
                listIngredients.add(drinkFull.strIngredient9 ?: "")
                listIngredients.add(drinkFull.strIngredient10 ?: "")
                listIngredients.add(drinkFull.strIngredient11 ?: "")
                listIngredients.add(drinkFull.strIngredient12 ?: "")
                listIngredients.add(drinkFull.strIngredient13 ?: "")
                listIngredients.add(drinkFull.strIngredient14 ?: "")
                listIngredients.add(drinkFull.strIngredient15 ?: "")


                val listMeasures = mutableListOf<String>()
                listMeasures.add(drinkFull.strMeasure1 ?: "")
                listMeasures.add(drinkFull.strMeasure2 ?: "")
                listMeasures.add(drinkFull.strMeasure3 ?: "")
                listMeasures.add(drinkFull.strMeasure4 ?: "")
                listMeasures.add(drinkFull.strMeasure5 ?: "")
                listMeasures.add(drinkFull.strMeasure6 ?: "")

                listMeasures.add(drinkFull.strMeasure7 ?: "")
                listMeasures.add(drinkFull.strMeasure8 ?: "")
                listMeasures.add(drinkFull.strMeasure9 ?: "")
                listMeasures.add(drinkFull.strMeasure10 ?: "")
                listMeasures.add(drinkFull.strMeasure11 ?: "")
                listMeasures.add(drinkFull.strMeasure12 ?: "")
                listMeasures.add(drinkFull.strMeasure13 ?: "")
                listMeasures.add(drinkFull.strMeasure14 ?: "")
                listMeasures.add(drinkFull.strMeasure15 ?: "")

                 drinkImage?.let {
                      item {
                          val bitmap = loadPicture(url = it, defaultImage = DEFAULT_DRINK_IMAGE).value
                          bitmap?.let {
                              Box(modifier = Modifier.fillMaxWidth()) {
                                  Image(
                                      bitmap = bitmap.asImageBitmap(),
                                      contentDescription = drinkTitle,
                                      contentScale = ContentScale.Crop,
                                      modifier = Modifier
                                          .fillMaxWidth()
                                          .requiredHeight(IMAGE_HEIGHT.dp)
                                  )

                                  Icon(
                                      imageVector = Icons.Filled.Favorite,
                                      contentDescription = stringResource(id = R.string.favorite),
                                      tint =
                                     if(stateLike.data!=null && stateLike.data == true) Color.Red else Color.Gray,
                                              modifier = Modifier
                                                  .size(50.dp)
                                                  .padding(5.dp)
                                                  .clickable {
                                                      viewModel.changeLike(idCocktail.toLong(), drinkTitle, drinkImage)
                                                  },
                                  )
                              }
                          }
                      }
                }

                item {
                    Text(
                        text = drinkTitle ?: "no title",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        style = MaterialTheme.typography.h4
                    )
                }

                item {
                    Text(
                        text = instructions ?: "no instructions",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        style = MaterialTheme.typography.h6
                    )
                }

                listIngredients.forEachIndexed { index, one ->

                    one?.let {
                        if (it.isNotEmpty()) {
                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                ) {
                                    Text(
                                        text = it,
                                        modifier = Modifier
                                            .fillMaxWidth(0.65f)
                                            .wrapContentWidth(Alignment.Start),
                                        style = MaterialTheme.typography.h5
                                    )


                                    val measure = listMeasures[index]
                                    Text(
                                        text = measure,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentWidth(Alignment.End)
                                            .align(CenterVertically),
                                        style = MaterialTheme.typography.h6
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
            }
        }


        //Error
        if (stateById.error.isNotEmpty()) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = CenterVertically
            ) {
                Text(text = stateById.error)
            }
        }
        if (stateById.loading) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = CenterVertically
            ){
                CircularProgressIndicator()
            }
        }

    }

}