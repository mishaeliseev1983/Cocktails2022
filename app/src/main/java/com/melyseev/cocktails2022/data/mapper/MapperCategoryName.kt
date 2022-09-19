package com.melyseev.cocktails2022.data.mapper

import com.melyseev.cocktails2022.data.remote.dto.list_categories.alcoholic.ListAlcoholicsDto
import com.melyseev.cocktails2022.data.remote.dto.list_categories.category.ListCategoriesDto
import com.melyseev.cocktails2022.data.remote.dto.list_categories.common.CommonName
import com.melyseev.cocktails2022.data.remote.dto.list_categories.common.ListCommonDto
import com.melyseev.cocktails2022.data.remote.dto.list_categories.glass.ListGlassesDto
import com.melyseev.cocktails2022.data.remote.dto.list_categories.ingredients.ListIngredientsDto


object MapperCategoryName {
    fun fromAlcogolicDtoToCommonDto(value: ListAlcoholicsDto): ListCommonDto {
       return ListCommonDto(value.drinks.map { CommonName(categoryName = it.strAlcoholic) })
    }
    fun fromCategoryDtoToCommonDto(value: ListCategoriesDto): ListCommonDto {
        return ListCommonDto(value.drinks.map { CommonName(categoryName = it.strCategory) })
    }
    fun fromGlassDtoToCommonDto(value: ListGlassesDto): ListCommonDto {
        return ListCommonDto(value.drinks.map { CommonName(categoryName = it.strGlass) })
    }
    fun fromIngredientDtoToCommonDto(value: ListIngredientsDto): ListCommonDto {
        return ListCommonDto(value.drinks.map { CommonName(categoryName = it.strIngredient1) })
    }
}