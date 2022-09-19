package com.melyseev.cocktails2022.domain.repository

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.MutableState

interface CocktailRepositoryUploadImage {
    suspend fun uploadImage(srcImage: String, stateBitmap: MutableState<Bitmap>)
    fun uploadDefaultImage(context: Context): MutableState<Bitmap>
}