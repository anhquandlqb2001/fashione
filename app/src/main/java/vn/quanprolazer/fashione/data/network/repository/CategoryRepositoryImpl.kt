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
import vn.quanprolazer.fashione.data.domain.model.Resource
import vn.quanprolazer.fashione.data.domain.repository.CategoryRepository
import vn.quanprolazer.fashione.data.network.mapper.NetworkCategoryListMapper
import vn.quanprolazer.fashione.data.network.service.CategoryService


class CategoryRepositoryImpl @AssistedInject constructor(
    private val categoryService: CategoryService,
    @Assisted private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CategoryRepository {

    override suspend fun getCategoryList(): Resource<List<Category>> {

        val result = withContext(defaultDispatcher) {
            categoryService.getCategoryList()
        }

        return when (result) {
            is Resource.Success -> Resource.Success(NetworkCategoryListMapper.map(result.data))
            is Resource.Error -> Resource.Error(result.exception)
            else -> Resource.Loading(null)
        }
    }

    override suspend fun getCategoryListFromLocal(): Resource<List<Category>> {
        val result = withContext(defaultDispatcher) {
            categoryService.getCategoryList(Source.CACHE)
        }

        return when (result) {
            is Resource.Success -> Resource.Success(NetworkCategoryListMapper.map(result.data))
            is Resource.Error -> Resource.Error(result.exception)
            else -> Resource.Loading(null)
        }
    }

}