/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.repository

import com.google.firebase.firestore.Source
import vn.quanprolazer.fashione.domain.model.Product
import vn.quanprolazer.fashione.domain.model.ProductDetail
import vn.quanprolazer.fashione.domain.model.Result

interface ProductRepository {

    suspend fun getProducts(source: Source = Source.DEFAULT): Result<List<Product>>

    suspend fun getProductsByCategoryId(categoryId: String): List<Product>

    suspend fun findProductsByQuery(query: String): List<Product>

    suspend fun getProductDetailByProductId(productId: String): ProductDetail

}