/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.repositories

import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vn.quanprolazer.fashione.data.network.models.toDomainModel
import vn.quanprolazer.fashione.data.network.services.firestores.CategoryService
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.repositories.CategoryRepository


class CategoryRepositoryImpl @AssistedInject constructor(
    private val categoryService: CategoryService,
    @Assisted private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CategoryRepository {

    override suspend fun getCategoryList() = try {
        val response = withContext(defaultDispatcher) {
            categoryService.getCategoryList()
        }
        Resource.Success(response.map { it.toDomainModel() })
    } catch (e: Exception) {
        Resource.Error(e)
    }

}