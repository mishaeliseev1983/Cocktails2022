package com.melyseev.cocktails2022.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = LikeEntity.TABLE_LIKE)
data class LikeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = COCKTAIL_ID)   var cocktailId: Long,

    @ColumnInfo(name = COCKTAIL_TITLE)   var cocktailTitle: String,
    @ColumnInfo(name = COCKTAIL_IMAGE)   var cocktailImage: String,
    @ColumnInfo(name = LIKE)   var like: Boolean = true,
){
    companion object {
        const val TABLE_LIKE = "TABLE_LIKE"
        const val COCKTAIL_ID = "COCKTAIL_ID"
        const val LIKE = "LIKE"
        const val COCKTAIL_TITLE = "COCKTAIL_TITLE"
        const val COCKTAIL_IMAGE = "COCKTAIL_IMAGE"
    }
}

