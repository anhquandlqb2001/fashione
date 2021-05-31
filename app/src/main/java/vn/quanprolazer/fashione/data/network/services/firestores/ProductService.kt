/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import vn.quanprolazer.fashione.data.network.models.*

interface ProductService {

    suspend fun getProducts(): List<NetworkProduct>

    suspend fun getProductsByCategoryId(categoryId: String): List<NetworkProduct>

    suspend fun getProductDetailByProductId(productId: String): NetworkProductDetail

    suspend fun getProductVariantsByProductId(productId: String): List<NetworkProductVariant>

    suspend fun getProductVariantOptionsByVariantId(variantId: String): List<NetworkProductVariantOption>

    suspend fun getProductVariantOption(variantOptionId: String): NetworkProductVariantOption

    suspend fun getProductImagesByProductId(productId: String): List<NetworkProductImage>

    suspend fun getProductImageByProductId(productId: String): NetworkProductImage

    suspend fun getProductImageByVariantId(variantId: String): NetworkProductImage

    suspend fun getProductByProductId(productId: String): NetworkProduct
}