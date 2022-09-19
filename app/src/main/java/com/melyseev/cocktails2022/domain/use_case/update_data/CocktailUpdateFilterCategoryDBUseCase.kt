package com.melyseev.cocktails2022.domain.use_case.update_data
import com.melyseev.cocktails2022.domain.repository.CocktailRepositoryDataBase
import javax.inject.Inject

class CocktailUpdateFilterCategoryDBUseCase @Inject constructor(
    private val repositoryFromDB: CocktailRepositoryDataBase
) {
    suspend fun execute(filterName: String, filterCategoryName: String, isChecked: Boolean): Boolean{
        val listChecked = repositoryFromDB.getCategoriesCheckedByFilterName(filterName = filterName)
        if(listChecked.size==1 && !isChecked) return false
        repositoryFromDB.updateCheckCategory(filterName = filterName, filterCategoryName = filterCategoryName, isCheckedCategory = isChecked)
        return true
    }
}