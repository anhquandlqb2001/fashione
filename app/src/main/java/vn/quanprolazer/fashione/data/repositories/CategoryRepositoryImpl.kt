/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.repositories

import com.google.firebase.firestore.Source
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vn.quanprolazer.fashione.data.network.models.toDomainModel
import vn.quanprolazer.fashione.data.network.services.firestores.CategoryService
import vn.quanprolazer.fashione.domain.models.Category
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.repositories.CategoryRepository


class CategoryRepositoryImpl @AssistedInject constructor(
    private val categoryService: CategoryService,
    @Assisted private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CategoryRepository {

    override suspend fun getCategoryList(): Resource<List<Category>> {

        val result = withContext(defaultDispatcher) {
            categoryService.getCategoryList()
        }

        return when (result) {
            is Resource.Success -> Resource.Success(result.data.map { it.toDomainModel() })
            is Resource.Error -> Resource.Error(result.exception)
            else -> Resource.Loading(null)
        }
    }

    override suspend fun getCategoryListFromLocal(): Resource<List<Category>> {
        val result = withContext(defaultDispatcher) {
            categoryService.getCategoryList(Source.CACHE)
        }

        return when (result) {
            is Resource.Success -> Resource.Success(result.data.map { it.toDomainModel() })
            is Resource.Error -> Resource.Error(result.exception)
            else -> Resource.Loading(null)
        }
    }

}