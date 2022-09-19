package com.melyseev.cocktails2022.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.melyseev.cocktails2022.data.database.entity.FilterEntity

@Dao
interface FiltersDao {

@Insert
suspend fun insert(entity: FilterEntity)

@Query("SELECT * from ${FilterEntity.TABLE_FILTER}")
suspend fun getAll(): List<FilterEntity>
}