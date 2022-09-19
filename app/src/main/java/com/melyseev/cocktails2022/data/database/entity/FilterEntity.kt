package com.melyseev.cocktails2022.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = FilterEntity.TABLE_FILTER)
data class FilterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = FILTER_NAME)   var name: String,
){
    companion object {
        const val TABLE_FILTER = "TABLE_FILTER"
        const val FILTER_NAME = "FILTER_NAME"
    }
}