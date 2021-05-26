/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import com.google.firebase.firestore.Source
import vn.quanprolazer.fashione.data.network.models.*
import vn.quanprolazer.fashione.domain.models.Resource

interface ProductService {

    suspend fun getProducts(): Resource<List<NetworkProduct>>

    suspend fun getProductsByCategoryId(categoryId: String): Resource<List<NetworkProduct>>

    suspend fun getProductDetailByProductId(productId: String): Resource<NetworkProductDetail>

    suspend fun getProductVariantsByProductId(productId: String): Resource<List<NetworkProductVariant>>

    suspend fun getProductVariantOptionsByVariantId(variantId: String): Resource<List<NetworkProductVariantOption>>

    suspend fun getProductVariantOption(variantOptionId: String): Resource<NetworkProductVariantOption>

    suspend fun getProductImagesByProductId(productId: String): Resource<List<NetworkProductImage>>

    suspend fun getProductImageByProductId(productId: String): Resource<NetworkProductImage>

    suspend fun getProductImageByVariantId(variantId: String): Resource<NetworkProductImage>

    suspend fun getProductByProductId(productId: String): Resource<NetworkProduct>
}