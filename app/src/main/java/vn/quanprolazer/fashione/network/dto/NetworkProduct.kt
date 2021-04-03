/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network.dto

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
    @SerialName("detail")
    val detail: NetworkProductDetail = NetworkProductDetail()
)

//@Serializable
//data class NetworkProductDetail (
//    val detailId: String = "",
//    val productId: String = "",
//    val qty: Int = -1,
//    val description: String = "",
//    val images: List<NetworkProductImage> = listOf(
//        EmptyNetworkProductImage
//    ),
//    val variants: List<NetworkProductVariant> = listOf(
//        EmptyNetworkProductVariant
//    )
//)


//interface NetworkProductDetail {
//    val detailId: String
//    val productId: String
//    val qty: Int
//    val description: String
//    val images: List<NetworkProductImage>
//    val variants: List<NetworkProductVariant>
//}


@Serializable
data class NetworkProductDetail(
    @SerialName("id")
    val id: String = "",
    @SerialName("product_id")
    val product_id: String = "",
    @SerialName("qty")
    val qty: Int = -1,
    @SerialName("description")
    val description: String = "",
    @SerialName("images")
    val images: List<NetworkProductImage> = listOf(NetworkProductImage()),
    @SerialName("variants")
    val variants: List<NetworkProductVariant> = listOf(NetworkProductVariant())
)

//object EmptyNetworkProductDetail : NetworkProductDetail {
//    override val detailId: String = ""
//    override val productId: String = ""
//    override val qty: Int = -1
//    override val description: String = ""
//    override val images: List<NetworkProductImage> = listOf(EmptyNetworkProductImage)
//    override val variants: List<NetworkProductVariant> = listOf(EmptyNetworkProductVariant)
//}

@Serializable
data class NetworkProductImage(
    val url: String = ""
)

@Serializable
data class NetworkProductVariant(
    val name: String = "",
    val size: String = "",
    val qty: Int = -1
)
