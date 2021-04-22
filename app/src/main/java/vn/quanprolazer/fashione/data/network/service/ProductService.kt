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

    suspend fun getProductsByCategoryId(categoryId: String): Result<List<NetworkProduct>>

    suspend fun getProductDetailByProductId(productId: String): Result<NetworkProductDetail>

    suspend fun getProductVariantsByProductId(productId: String): Result<List<NetworkProductVariant>>

    suspend fun getProductVariantOptionsByVariantId(variantId: String): Result<List<NetworkProductVariantOption>>

    suspend fun getProductImagesByProductId(productId: String): Result<List<NetworkProductImage>>

    suspend fun getProductImageByVariantId(variantId: String): Result<NetworkProductImage>

    suspend fun getProductByProductId(productId: String): Result<NetworkProduct>
}