/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network.repository

import vn.quanprolazer.fashione.domain.model.Category
import vn.quanprolazer.fashione.domain.repository.CategoryRepository
import vn.quanprolazer.fashione.network.mapper.CategoryListMapper
import vn.quanprolazer.fashione.network.service.CategoryServiceImpl


object CategoryRepositoryImpl : CategoryRepository {
    override suspend fun getCategoryList(): List<Category> {
        return CategoryListMapper.map(CategoryServiceImpl.getCategoryList())
    }
}