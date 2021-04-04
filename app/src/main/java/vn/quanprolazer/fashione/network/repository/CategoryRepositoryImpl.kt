/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network.repository

import com.google.firebase.firestore.Source
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vn.quanprolazer.fashione.domain.model.Category
import vn.quanprolazer.fashione.domain.model.Result
import vn.quanprolazer.fashione.domain.repository.CategoryRepository
import vn.quanprolazer.fashione.network.mapper.CategoryListMapper
import vn.quanprolazer.fashione.network.service.CategoryService


class CategoryRepositoryImpl(
    private val categoryService: CategoryService,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CategoryRepository {

    override suspend fun getCategoryList(): Result<List<Category>> {

        val result = withContext(defaultDispatcher) {
            categoryService.getCategoryList()
        }

        return when(result) {
            is Result.Success -> Result.Success(CategoryListMapper.map(result.data))
            is Result.Error -> Result.Error(result.exception)
        }
    }

    override suspend fun getCategoryListFromLocal(): Result<List<Category>> {
        val result = withContext(defaultDispatcher) {
            categoryService.getCategoryList(Source.CACHE)
        }

        return when(result) {
            is Result.Success -> Result.Success(CategoryListMapper.map(result.data))
            is Result.Error -> Result.Error(result.exception)
        }
    }
}