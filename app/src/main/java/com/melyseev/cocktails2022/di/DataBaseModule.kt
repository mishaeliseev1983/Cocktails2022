package com.melyseev.cocktails2022.di

import androidx.room.Room
import com.melyseev.cocktails2022.CocktailsApp
import com.melyseev.cocktails2022.data.database.CocktailFiltersDataBase
import com.melyseev.cocktails2022.data.database.dao.FilterCategoriesDao
import com.melyseev.cocktails2022.data.database.dao.FiltersDao
import com.melyseev.cocktails2022.data.database.dao.LikesDao
import com.melyseev.cocktails2022.data.repository.CocktailRepositoryDataBaseImpl
import com.melyseev.cocktails2022.domain.repository.CocktailRepositoryDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideDataBase(app: CocktailsApp): CocktailFiltersDataBase{
        return Room.databaseBuilder(app, CocktailFiltersDataBase::class.java, CocktailFiltersDataBase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun filterDao(appDatabase: CocktailFiltersDataBase): FiltersDao {
        return appDatabase.filtersDao()
    }

    @Singleton
    @Provides
    fun categoriesDao(appDatabase: CocktailFiltersDataBase): FilterCategoriesDao {
        return appDatabase.categoriesDao()
    }

    @Singleton
    @Provides
    fun likesDao(appDatabase: CocktailFiltersDataBase): LikesDao {
        return appDatabase.likesDao()
    }

    @Singleton
    @Provides
    fun repositoryDaoCategory(filtersDao: FiltersDao, filterCategoriesDao: FilterCategoriesDao, likesDao: LikesDao
    ): CocktailRepositoryDataBase {
        return CocktailRepositoryDataBaseImpl(filtersDao, filterCategoriesDao, likesDao)
    }
}