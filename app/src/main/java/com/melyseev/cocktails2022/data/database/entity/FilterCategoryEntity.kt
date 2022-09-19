package com.melyseev.cocktails2022.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = FilterCategoryEntity.TABLE_FILTER_CATEGORY)
data class FilterCategoryEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = ID_FILTER) val idFilter: Long,
    @ColumnInfo(name = CATEGORY_NAME) var categoryName: String,
    @ColumnInfo(name = CATEGORY_CHECKED) var categoryChecked: Boolean,
){
    companion object {
        const val TABLE_FILTER_CATEGORY= "TABLE_FILTER_CATEGORY"
        const val ID_FILTER = "ID_FILTER"
        const val CATEGORY_NAME = "CATEGORY_NAME"
        const val CATEGORY_CHECKED = "CATEGORY_CHECKED"
    }
}