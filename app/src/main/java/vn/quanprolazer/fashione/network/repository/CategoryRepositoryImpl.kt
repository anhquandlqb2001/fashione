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
import vn.quanprolazer.fashione.domain.repository.CategoryRepository
import vn.quanprolazer.fashione.network.mapper.CategoryListMapper
import vn.quanprolazer.fashione.network.service.CategoryService
import vn.quanprolazer.fashione.network.service.CategoryServiceImpl


class CategoryRepositoryImpl(
    private val categoryService: CategoryService,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CategoryRepository {

    override suspend fun getCategoryList(): List<Category> {
        return withContext(defaultDispatcher) {
            CategoryListMapper.map(categoryService.getCategoryList())
        }
    }

    override suspend fun getCategoryListFromLocal(): List<Category> {
        return withContext(defaultDispatcher) {
            CategoryListMapper.map(categoryService.getCategoryList(Source.CACHE))
        }
    }
}