/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.repository

import com.google.firebase.firestore.Source
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vn.quanprolazer.fashione.data.domain.model.Category
import vn.quanprolazer.fashione.data.domain.model.Result
import vn.quanprolazer.fashione.data.domain.repository.CategoryRepository
import vn.quanprolazer.fashione.data.network.mapper.NetworkCategoryListMapper
import vn.quanprolazer.fashione.data.network.service.CategoryService


class CategoryRepositoryImpl @AssistedInject constructor(
    private val categoryService: CategoryService,
    @Assisted private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CategoryRepository {

    override suspend fun getCategoryList(): Result<List<Category>> {

        val result = withContext(defaultDispatcher) {
            categoryService.getCategoryList()
        }

        return when (result) {
            is Result.Success -> Result.Success(NetworkCategoryListMapper.map(result.data))
            is Result.Error -> Result.Error(result.exception)
        }
    }

    override suspend fun getCategoryListFromLocal(): Result<List<Category>> {
        val result = withContext(defaultDispatcher) {
            categoryService.getCategoryList(Source.CACHE)
        }

        return when (result) {
            is Result.Success -> Result.Success(NetworkCategoryListMapper.map(result.data))
            is Result.Error -> Result.Error(result.exception)
        }
    }
}