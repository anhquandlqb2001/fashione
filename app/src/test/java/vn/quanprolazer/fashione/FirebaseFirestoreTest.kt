/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione

import org.junit.Test
import vn.quanprolazer.fashione.domain.model.Category
import vn.quanprolazer.fashione.network.dto.NetworkCategory
import vn.quanprolazer.fashione.network.mapper.CategoryListMapper
import vn.quanprolazer.fashione.network.repository.CategoryRepositoryImpl
import vn.quanprolazer.fashione.network.service.CategoryServiceImpl

private fun mapProducts(networkProductList: List<NetworkCategory>): List<Category> {
    return networkProductList.map {
        productDataMapper.map(DataProduct(it, productPreferences.isFavourite(it.id)))
    }
}

class FirebaseFirestoreTest {
    @Test
    suspend fun `get category`() {

        val categoryServiceImpl = CategoryServiceImpl()

        val categories = categoryServiceImpl.getCategoryList()

        val category = CategoryRepositoryImpl(categoryServiceImpl, mapProducts(categories.))
    }
}