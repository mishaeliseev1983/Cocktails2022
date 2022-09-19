package com.melyseev.cocktails2022.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.melyseev.cocktails2022.common.Constants
import com.melyseev.cocktails2022.data.datasharedpreferences.DataSharedPreferencesFilterName
import com.melyseev.cocktails2022.domain.repository.CocktailRepositorySharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val FILTERNAME_KEY = "FILTERNAME_KEY"
private const val FILTERCATEGORYNAME_KEY = "FILTERCATEGORYNAME_KEY"
private const val DEFAULT_FILTERNAME = Constants.FILTER_ALCOHOLIC
private const val DEFAULT_FILTERCATEGORYNAME = Constants.FILTER_ALCOHOLIC

class CocktailRepositorySharedPreferencesImpl
@Inject constructor(private val applicationContext: Context, private val data: SharedPreferences):
    CocktailRepositorySharedPreferences {

    override fun getContext(): Context {
            return applicationContext
    }


    override fun getFilter(): DataSharedPreferencesFilterName {

        val filterName = data.getString(FILTERNAME_KEY, DEFAULT_FILTERNAME) ?: DEFAULT_FILTERNAME
        val filterCategoryName = data.getString(FILTERCATEGORYNAME_KEY, DEFAULT_FILTERCATEGORYNAME) ?: DEFAULT_FILTERCATEGORYNAME
        return  DataSharedPreferencesFilterName(filterName ,filterCategoryName)
    }

    override fun setFilter(filter: DataSharedPreferencesFilterName) {
        data.edit{
            putString(FILTERNAME_KEY, filter.filterName)
            putString(FILTERCATEGORYNAME_KEY, filter.filterCategoryName)
        }
    }

}