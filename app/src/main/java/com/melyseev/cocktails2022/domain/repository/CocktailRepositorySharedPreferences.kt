package com.melyseev.cocktails2022.domain.repository

import android.content.Context
import com.melyseev.cocktails2022.data.datasharedpreferences.DataSharedPreferencesFilterName

interface CocktailRepositorySharedPreferences {

    fun getContext(): Context
    fun getFilter(): DataSharedPreferencesFilterName
    fun setFilter(dataSharedPreferencesFilterName: DataSharedPreferencesFilterName)
}