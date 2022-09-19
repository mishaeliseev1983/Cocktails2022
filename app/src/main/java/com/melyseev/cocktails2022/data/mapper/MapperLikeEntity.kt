package com.melyseev.cocktails2022.data.mapper

import com.melyseev.cocktails2022.data.database.entity.LikeEntity
import com.melyseev.cocktails2022.data.remote.dto.list_categories.alcoholic.ListAlcoholicsDto
import com.melyseev.cocktails2022.data.remote.dto.list_categories.common.CommonName
import com.melyseev.cocktails2022.data.remote.dto.list_categories.common.ListCommonDto
import com.melyseev.cocktails2022.data.remote.dto.short_describe_cocktail.Drink
import com.melyseev.cocktails2022.data.remote.dto.short_describe_cocktail.ShortDto

object MapperLikeEntity {

    fun fromListLikeEntityToShortDto(likeEntities: List<LikeEntity>): ShortDto {
        val resultListDrinks  = mutableListOf<Drink>()
        likeEntities.forEach {
            resultListDrinks.add( Drink(it.cocktailId.toString(), it.cocktailTitle, it.cocktailImage))
        }
        return ShortDto(resultListDrinks)
    }
}