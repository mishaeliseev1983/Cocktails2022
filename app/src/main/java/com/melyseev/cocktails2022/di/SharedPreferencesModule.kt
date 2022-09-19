package com.melyseev.cocktails2022.di

import android.content.Context
import com.melyseev.cocktails2022.data.repository.CocktailRepositorySharedPreferencesImpl
import com.melyseev.cocktails2022.domain.repository.CocktailRepositorySharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  SharedPreferencesModule {

    private const val USER_PREFERENCES_NAME = "user_preferences"

    @Singleton
    @Provides
    fun getAppContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }

    @Singleton
    @Provides
    fun getSharedPreferences(@ApplicationContext appContext: Context): CocktailRepositorySharedPreferences{
        val cocktailRepositorySharedPreferences = CocktailRepositorySharedPreferencesImpl(appContext.applicationContext, appContext.applicationContext.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE))
        return cocktailRepositorySharedPreferences
    }
}