/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ReviewStatus(val status: Int) {
    @SerialName("YES")
    YES(1),

    @SerialName("NO")
    NO(0)
}


data class Purchase(
    val id: String, // order item id
    val userId: String,
    val productId: String,
    val variantId: String,
    val variantOptionId: String,
    val productName: String,
    val variantName: String,
    val variantValue: String,
    var quantity: Int,
    val price: String,
    var purchaseImage: Resource<ProductImage>? = null,
    val status: OrderStatus,
//    val reviewStatus: ReviewStatus
)