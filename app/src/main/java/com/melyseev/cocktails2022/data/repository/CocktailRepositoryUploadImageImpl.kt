package com.melyseev.cocktails2022.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.melyseev.cocktails2022.common.Constants.DEFAULT_DRINK_IMAGE
import com.melyseev.cocktails2022.domain.repository.CocktailRepositoryUploadImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.*

class CocktailRepositoryUploadImageImpl : CocktailRepositoryUploadImage {

    private val weakHashMap = WeakHashMap<String, Bitmap>(10)
    override suspend fun uploadImage(srcImage: String, stateBitmap: MutableState<Bitmap>){
        if(weakHashMap.containsKey(srcImage)) {
            weakHashMap.get(srcImage)?.let {
                stateBitmap.value = it
                return
            }
        }

        withContext(Dispatchers.Default) {
            try {
                var inputStream = URL(srcImage).openStream()
                val bitmap =  BitmapFactory.decodeStream(inputStream)
                weakHashMap.put(srcImage, bitmap)
                stateBitmap.value = bitmap

            } catch (e: IOException) {
                e.printStackTrace()
                println("uploadImage error = $e")
            }
        }
    }

    override fun uploadDefaultImage(context: Context): MutableState<Bitmap> =
       mutableStateOf(BitmapFactory.decodeResource(context.resources, DEFAULT_DRINK_IMAGE))

}