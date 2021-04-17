/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class NetworkCartItem(
    @SerialName("product_id")
    val productId: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("variant_id")
    var variantId: String = "",
    @SerialName("variant_option_id")
    var variantOptionId: String = "",
    @SerialName("variant_name")
    var variantName: String = "",
    @SerialName("variant_value")
    var variantValue: String = "",
    var quantity: Int = 0,
    var price: String = "0"
)




