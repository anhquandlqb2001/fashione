/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.dto

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DataTransferObjects go in this file. These are responsible for parsing responses from the server
 * or formatting objects to send to the server. You should convert these to domain objects before
 * using them.
 *
 */


/**
 * Model name: products
 */
@Serializable
data class NetworkProduct(
    @SerialName("id")
    @DocumentId
    val id: String? = "",
    @set:PropertyName("category_id")
    @get:PropertyName("category_id")
    @SerialName("category_id")
    var categoryId: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("thumbnail_url")
    @set:PropertyName("thumbnail_url")
    @get:PropertyName("thumbnail_url")
    var thumbnailUrl: String = "",
    @SerialName("price")
    val price: String = "",
//    @SerialName("detail")
//    val detail: NetworkProductDetail = NetworkProductDetail()
)


/**
 * Model name: product_details
 */
@Serializable
data class NetworkProductDetail(
    @DocumentId
    @SerialName("id")
    val id: String = "",
    @set:PropertyName("product_id")
    @get:PropertyName("product_id")
    @SerialName("product_id")
    var productId: String = "",
    @SerialName("description")
    val description: String = ""
)


/**
 * Sub field of model product_details
 * Field of [NetworkProductDetail]
 */
@Serializable
data class NetworkProductImage(
    @DocumentId
    val id: String = "",
    @set:PropertyName("product_id")
    @get:PropertyName("product_id")
    var productId: String = "",
    @set:PropertyName("variant_id")
    @get:PropertyName("variant_id")
    var variantId: String = "",
    @set:PropertyName("variant_option_id")
    @get:PropertyName("variant_option_id")
    var variantOptionId: String = "",
    val url: String = ""
)


/**
 * Model name: product_variants
 */
@Serializable
data class NetworkProductVariant(
    @DocumentId
    val id: String = "",
    @set:PropertyName("product_id")
    @get:PropertyName("product_id")
    var productId: String = "",
    val name: String = "",
)

/**
 * Model name: product_variant_options
 */
@Serializable
data class NetworkProductVariantOption(
    @DocumentId
    val id: String = "",
    @set:PropertyName("variant_id")
    @get:PropertyName("variant_id")
    var variantId: String = "",
    val value: String = "",
    val quantity: Int = -1,
    val price: String = "0"
)
