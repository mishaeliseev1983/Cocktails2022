package com.melyseev.cocktails2022.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.melyseev.cocktails2022.data.database.entity.FilterCategoryEntity


@Dao
interface FilterCategoriesDao {

    @Insert
    suspend fun insert(entity: FilterCategoryEntity)

    @Query("SELECT * from ${FilterCategoryEntity.TABLE_FILTER_CATEGORY} where  `${FilterCategoryEntity.ID_FILTER}` = :filterId and    `${FilterCategoryEntity.CATEGORY_CHECKED}` = 1")
    suspend fun getCategoriesCheckedByFilter(filterId: Long): List<FilterCategoryEntity>

    @Query("SELECT * from ${FilterCategoryEntity.TABLE_FILTER_CATEGORY} where  `${FilterCategoryEntity.ID_FILTER}` = :filterId")
    suspend fun getCategoriesAllByFilter(filterId: Long): List<FilterCategoryEntity>

    @Update
    suspend fun update(entity: FilterCategoryEntity)

    @Query("SELECT * from ${FilterCategoryEntity.TABLE_FILTER_CATEGORY} where  `${FilterCategoryEntity.ID_FILTER}` = :filterId and    `${FilterCategoryEntity.CATEGORY_NAME}` = :filterCategoryName")
    suspend fun getCategoryByName(filterId: Long, filterCategoryName: String): FilterCategoryEntity
}