package com.melyseev.cocktails2022.di

import com.google.gson.GsonBuilder
import com.melyseev.cocktails2022.common.Constants
import com.melyseev.cocktails2022.data.mapper.MapperCategoryName
import com.melyseev.cocktails2022.data.remote.RetrofitService
import com.melyseev.cocktails2022.data.repository.CocktailRepositoryByIdImpl
import com.melyseev.cocktails2022.data.repository.CocktailRepositoryFiltersFromInternetImpl
import com.melyseev.cocktails2022.data.repository.CocktailRepositoryListImpl
import com.melyseev.cocktails2022.domain.repository.CocktailRepositoryById
import com.melyseev.cocktails2022.domain.repository.CocktailRepositoryFiltersFromInternet
import com.melyseev.cocktails2022.domain.repository.CocktailRepositoryList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Headers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideRecipeService(): RetrofitService {
        return Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(RetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun repositoryRetrofit(retrofitService: RetrofitService): CocktailRepositoryList {
        return CocktailRepositoryListImpl(retrofitService = retrofitService)
    }

    @Singleton
    @Provides
    fun repositoryFiltersFromInternet(retrofitService: RetrofitService): CocktailRepositoryFiltersFromInternet {
        return CocktailRepositoryFiltersFromInternetImpl(retrofitService = retrofitService, mapperCategoryName = repositoryRetrofitMapperToCommonNameCategory())
    }

    @Singleton
    @Provides
    fun repositoryRetrofitById(retrofitService: RetrofitService): CocktailRepositoryById {
        return CocktailRepositoryByIdImpl(retrofitService = retrofitService)
    }

    @Singleton
    @Provides
    fun repositoryRetrofitMapperToCommonNameCategory(): MapperCategoryName {
        return MapperCategoryName
    }
}