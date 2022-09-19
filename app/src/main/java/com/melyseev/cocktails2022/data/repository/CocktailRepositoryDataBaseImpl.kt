package com.melyseev.cocktails2022.data.repository

import com.melyseev.cocktails2022.common.Constants.FILTER_ALCOHOLIC
import com.melyseev.cocktails2022.common.Constants.FILTER_CATEGORY
import com.melyseev.cocktails2022.common.Constants.FILTER_FAVORITE
import com.melyseev.cocktails2022.common.Constants.FILTER_GLASS
import com.melyseev.cocktails2022.common.Constants.FILTER_INGREDIENTS
import com.melyseev.cocktails2022.data.database.dao.FilterCategoriesDao
import com.melyseev.cocktails2022.data.database.dao.FiltersDao
import com.melyseev.cocktails2022.data.database.dao.LikesDao
import com.melyseev.cocktails2022.data.database.entity.FilterCategoryEntity
import com.melyseev.cocktails2022.data.database.entity.FilterEntity
import com.melyseev.cocktails2022.data.database.entity.LikeEntity
import com.melyseev.cocktails2022.domain.repository.CocktailRepositoryDataBase
import javax.inject.Inject

class CocktailRepositoryDataBaseImpl @Inject constructor(
    val filtersDao: FiltersDao,
    private val categoriesDao: FilterCategoriesDao,
    private val likesDao: LikesDao
): CocktailRepositoryDataBase {

    override suspend fun isExistTableFilter(): Boolean {
        return filtersDao.getAll().isNotEmpty()
    }

    override suspend fun fillTableFilter() {
        if(!isExistTableFilter()){
            filtersDao.insert(FilterEntity(1, FILTER_ALCOHOLIC))
            filtersDao.insert(FilterEntity(2, FILTER_CATEGORY))
            filtersDao.insert(FilterEntity(3, FILTER_GLASS))
            filtersDao.insert(FilterEntity(4, FILTER_INGREDIENTS))
            filtersDao.insert(FilterEntity(5, FILTER_FAVORITE))
        }
    }

    override suspend fun getCategoriesCheckedByFilterName(filterName: String): List<FilterCategoryEntity> {
        val listFilters = filtersDao.getAll()
        if(listFilters.isNotEmpty()) {
            var idFilter = listFilters.first { it.name == filterName }.id
            return categoriesDao.getCategoriesCheckedByFilter(idFilter)
        }
        return emptyList()
    }

    override suspend fun getCategoriesAllByFilterName(filterName: String): List<FilterCategoryEntity> {
        val listFilters = filtersDao.getAll()
        if(listFilters.isNotEmpty()) {
            var idFilter = listFilters.first { it.name == filterName }.id
            return categoriesDao.getCategoriesAllByFilter(idFilter)
        }
        return emptyList()
    }

    override suspend fun fillTableFilterCategories(filterName: String, categories: List<String>) {
        val listFilters = filtersDao.getAll()
        val idFilter = listFilters.first { it.name == filterName }.id
        println("categories =  $categories")

        categories.forEachIndexed { index, categoryName ->
            var checked= false
            if(index<3) checked= true
            val filterCategoryEntity =
                FilterCategoryEntity(categoryName = categoryName, categoryChecked = checked, idFilter = idFilter)
            categoriesDao.insert(filterCategoryEntity)
        }
    }

    override suspend fun getFilterNames(): List<String> {
        return filtersDao.getAll().map { it.name }
    }

    override suspend fun updateCheckCategory(
        filterName: String,
        filterCategoryName: String,
        isCheckedCategory: Boolean
    ) {
        val listFilters = filtersDao.getAll()
        val idFilter = listFilters.first { it.name == filterName }.id
        val filterCategoryEntity =  categoriesDao.getCategoryByName(filterId = idFilter, filterCategoryName = filterCategoryName)
        filterCategoryEntity.categoryChecked = isCheckedCategory
        categoriesDao.update(filterCategoryEntity)
    }


    override suspend fun changeAndGetLike(
        cocktailId: Long,
        cocktailTitle: String,
        cocktailImage: String
    ): Boolean {

        var likeEntity =  likesDao.searchById(cocktailId)

        if(likeEntity!=null){
            likeEntity.like = !likeEntity.like
            likesDao.update(likeEntity)
        }else{
            likeEntity = LikeEntity(cocktailId = cocktailId, cocktailTitle = cocktailTitle, cocktailImage = cocktailImage)
            likesDao.insert(likeEntity)
        }
        return likeEntity.like
    }

    override suspend fun getLike(cocktailId: Long): Boolean {
        val likeEntity  = likesDao.searchById(cocktailId)
        return likeEntity?.like?:false
    }

    override suspend fun getListFavorite(): List<LikeEntity> {
        return likesDao.getListFavorite()
    }

}