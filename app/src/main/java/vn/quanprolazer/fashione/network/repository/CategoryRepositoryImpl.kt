/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vn.quanprolazer.fashione.domain.model.Category
import vn.quanprolazer.fashione.domain.repository.CategoryRepository
import vn.quanprolazer.fashione.network.mapper.CategoryListMapper
import vn.quanprolazer.fashione.network.service.CategoryService
import vn.quanprolazer.fashione.network.service.CategoryServiceImpl


class CategoryRepositoryImpl(private val categoryService: CategoryService) : CategoryRepository {
    override suspend fun getCategoryList(): List<Category> {
        return CategoryListMapper.map(categoryService.getCategoryList())
    }
}