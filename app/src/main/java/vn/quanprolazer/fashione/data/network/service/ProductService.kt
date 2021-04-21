/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.service

import com.google.firebase.firestore.Source
import vn.quanprolazer.fashione.data.domain.model.Result
import vn.quanprolazer.fashione.data.network.dto.*

interface ProductService {

    suspend fun getProducts(source: Source = Source.DEFAULT): Result<List<NetworkProduct>>

    suspend fun getProductsByCategoryId(categoryId: String): List<NetworkProduct>

    suspend fun getProductDetailByProductId(productId: String): NetworkProductDetail

    suspend fun getProductVariantsByProductId(productId: String): List<NetworkProductVariant>

    suspend fun getProductVariantOptionsByProductVariantId(variantId: String): List<NetworkProductVariantOption>

    suspend fun getProductImagesByProductId(productId: String): List<NetworkProductImage>

}