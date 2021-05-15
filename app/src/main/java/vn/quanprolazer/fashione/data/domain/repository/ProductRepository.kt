/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.repository

import com.google.firebase.firestore.Source
import vn.quanprolazer.fashione.data.domain.model.*

interface ProductRepository {

    suspend fun getProducts(source: Source = Source.DEFAULT): Resource<List<Product>>

    suspend fun getProductsByCategoryId(categoryId: String): Resource<List<Product>>

    suspend fun findProductsByQuery(query: String): Resource<List<Product>>

    suspend fun getProductDetailByProductId(productId: String): Resource<ProductDetail>

    suspend fun getProductVariantsByProductId(productId: String): Resource<MutableList<ProductVariant>>

    suspend fun getProductVariantOptionsByVariantId(variantId: String): Resource<List<ProductVariantOption>>

    suspend fun getProductVariantOption(variantOptionId: String): Resource<ProductVariantOption>

    suspend fun getProductImagesByProductId(productId: String): Resource<List<ProductImage>>

    suspend fun getProductImageByProductId(productId: String): Resource<ProductImage>

    suspend fun getProductImageByProductVariantId(variantId: String): Resource<ProductImage>

    suspend fun getProductByProductId(productId: String): Resource<Product>

    suspend fun addReview(review: Review, rating: Rating): Resource<Boolean>

}