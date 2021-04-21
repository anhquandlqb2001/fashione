/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.repository

import com.google.firebase.firestore.Source
import vn.quanprolazer.fashione.data.domain.model.*

interface ProductRepository {

    suspend fun getProducts(source: Source = Source.DEFAULT): Result<List<Product>>

    suspend fun getProductsByCategoryId(categoryId: String): List<Product>

    suspend fun findProductsByQuery(query: String): List<Product>

    suspend fun getProductDetailByProductId(productId: String): ProductDetail

    suspend fun getProductVariantsAndOptionsByProductId(productId: String): List<ProductVariant>

    suspend fun getProductImagesByProductId(productId: String): List<ProductImage>

}