package com.melyseev.cocktails2022.data.remote

import com.melyseev.cocktails2022.data.remote.dto.byid_describe_cocktail.ByIdDto
import com.melyseev.cocktails2022.data.remote.dto.list_categories.alcoholic.ListAlcoholicsDto
import com.melyseev.cocktails2022.data.remote.dto.list_categories.category.ListCategoriesDto
import com.melyseev.cocktails2022.data.remote.dto.short_describe_cocktail.ShortDto
import com.melyseev.cocktails2022.data.remote.dto.list_categories.glass.ListGlassesDto
import com.melyseev.cocktails2022.data.remote.dto.list_categories.ingredients.ListIngredientsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    /*
    www.thecocktaildb.com/api/json/v1/1/filter.php?c=Ordinary_Drink
     */
    @GET("filter.php")
    suspend fun getCocktailListByCategories(
        @Query("c") category: String
    ): ShortDto

    /*
    https://www.thecocktaildb.com/api/json/v1/1/filter.php?a=Non_alcoholic
     */
    @GET("filter.php")
    suspend fun getCocktailListByAlcoholic(
        @Query("a") alco: String
    ): ShortDto

    @GET("filter.php")
    suspend fun getCocktailListByIngredients(
        @Query("i") alco: String
    ): ShortDto
    @GET("filter.php")
    suspend fun getCocktailListByGlasses(
        @Query("g") alco: String
    ): ShortDto


    @GET("list.php")
    suspend fun getAllListCategoriesByCategories(
        @Query("c") list: String = "list"
    ): ListCategoriesDto

    @GET("list.php")
    suspend fun getAllListCategoriesByGlasses(
        @Query("g") list: String = "list"
    ): ListGlassesDto

    @GET("list.php")
    suspend fun getAllListCategoriesByIngredients(
        @Query("i") list: String = "list"
    ): ListIngredientsDto

    @GET("list.php")
    suspend fun getAllListCategoriesByAlcoholic(
        @Query("a") list: String = "list"
    ): ListAlcoholicsDto

    @GET("lookup.php")
    suspend fun getCocktailById(
        @Query("i") id: String
    ) : ByIdDto
}