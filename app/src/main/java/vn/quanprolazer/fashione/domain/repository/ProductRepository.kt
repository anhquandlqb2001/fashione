/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.repository

import vn.quanprolazer.fashione.domain.model.Product
import vn.quanprolazer.fashione.domain.model.ProductDetail

interface ProductRepository {

    suspend fun getProducts(): List<Product>

    suspend fun getProductsByCategoryId(categoryId: String): List<Product>

    suspend fun findProductsByQuery(query: String): List<Product>

    suspend fun getProductDetailByProductId(productId: String) : ProductDetail

}