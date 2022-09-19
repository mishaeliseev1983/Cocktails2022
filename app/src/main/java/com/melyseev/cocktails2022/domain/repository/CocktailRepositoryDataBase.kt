package com.melyseev.cocktails2022.domain.repository

import com.melyseev.cocktails2022.data.database.entity.FilterCategoryEntity
import com.melyseev.cocktails2022.data.database.entity.LikeEntity

interface CocktailRepositoryDataBase {
    suspend fun isExistTableFilter(): Boolean
    suspend fun fillTableFilter()
    suspend fun getCategoriesCheckedByFilterName(filterName: String): List<FilterCategoryEntity>
    suspend fun getCategoriesAllByFilterName(filterName: String): List<FilterCategoryEntity>
    suspend fun fillTableFilterCategories(filterName: String, categories: List<String>)
    suspend fun getFilterNames(): List<String>
    suspend fun updateCheckCategory(filterName: String, filterCategoryName: String, isCheckedCategory: Boolean)

    suspend fun changeAndGetLike(cocktailId: Long, cocktailTitle: String, cocktailImage: String):Boolean
    suspend fun getLike(cocktailId: Long):Boolean
    suspend fun getListFavorite(): List<LikeEntity>
}