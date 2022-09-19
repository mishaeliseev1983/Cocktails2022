package com.melyseev.cocktails2022.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.melyseev.cocktails2022.data.database.dao.FilterCategoriesDao
import com.melyseev.cocktails2022.data.database.dao.FiltersDao
import com.melyseev.cocktails2022.data.database.dao.LikesDao
import com.melyseev.cocktails2022.data.database.entity.FilterCategoryEntity
import com.melyseev.cocktails2022.data.database.entity.FilterEntity
import com.melyseev.cocktails2022.data.database.entity.LikeEntity


@Database(entities = [FilterEntity::class, FilterCategoryEntity::class, LikeEntity::class], version = 6, exportSchema = false)
abstract class CocktailFiltersDataBase  : RoomDatabase() {
    abstract fun filtersDao(): FiltersDao
    abstract fun categoriesDao(): FilterCategoriesDao
    abstract fun likesDao(): LikesDao
    companion object{
        val DATABASE_NAME = "cocktailsdb"
    }
}