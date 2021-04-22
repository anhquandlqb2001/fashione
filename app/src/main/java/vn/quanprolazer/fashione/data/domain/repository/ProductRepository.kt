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

    suspend fun getProductsByCategoryId(categoryId: String): Result<List<Product>>

    suspend fun findProductsByQuery(query: String): Result<List<Product>>

    suspend fun getProductDetailByProductId(productId: String): Result<ProductDetail>

    suspend fun getProductVariantsByProductId(productId: String): Result<MutableList<ProductVariant>>

    suspend fun getProductVariantOptionsByVariantId(variantId: String): Result<List<ProductVariantOption>>

    suspend fun getProductImagesByProductId(productId: String): Result<List<ProductImage>>

    suspend fun getProductImageByProductVariantId(variantId: String): Result<ProductImage>

    suspend fun getProductByProductId(productId: String): Result<Product>

}