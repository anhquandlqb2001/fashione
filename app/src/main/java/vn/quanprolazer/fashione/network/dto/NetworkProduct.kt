/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DataTransferObjects go in this file. These are responsible for parsing responses from the server
 * or formatting objects to send to the server. You should convert these to domain objects before
 * using them.
 *
 */

@Serializable
data class NetworkProduct(
    @SerialName("id")
    val id: String?,
    @SerialName("category_id")
    val category_id: String,
    @SerialName("name")
    val name: String,
    @SerialName("thumbnail_url")
    val thumbnail_url: String,
    @SerialName("price")
    val price: String,
    @SerialName("detail")
    val detail: NetworkProductDetail?

)

@Serializable
data class NetworkProductDetail(
    @SerialName("id")
    val id: String,
    @SerialName("product_id")
    val product_id: String,
    @SerialName("qty")
    val qty: Int,
    @SerialName("description")
    val description: String,
    @SerialName("images")
    val images: List<NetworkProductImage>,
    @SerialName("variants")
    val variants: List<NetworkProductVariant>
)

interface NetworkProductImage {
    val id: String
    val url: String
}

interface NetworkProductVariant {
    val name: String
    val size: String
    val qty: Number
}