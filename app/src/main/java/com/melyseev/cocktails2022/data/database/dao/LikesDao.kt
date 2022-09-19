package com.melyseev.cocktails2022.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.melyseev.cocktails2022.data.database.entity.LikeEntity

@Dao
interface LikesDao {

    @Insert
    suspend fun insert(entity: LikeEntity)

    @Query("SELECT * from ${LikeEntity.TABLE_LIKE} where  `${LikeEntity.COCKTAIL_ID}` = :cocktailId ")
    suspend fun searchById(cocktailId: Long): LikeEntity?

    @Query("SELECT * from ${LikeEntity.TABLE_LIKE} where  `${LikeEntity.LIKE}` == 1 ")
    suspend fun getListFavorite(): List<LikeEntity>

    @Update
    suspend fun update(entity: LikeEntity)
}