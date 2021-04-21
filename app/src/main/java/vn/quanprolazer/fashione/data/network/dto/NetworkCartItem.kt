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


@Serializable
data class NetworkCartItem(
    @DocumentId
    val id: String = "",
    @SerialName("product_id")
    @set:PropertyName("product_id")
    @get:PropertyName("product_id")
    var productId: String = "",
    @SerialName("user_id")
    @set:PropertyName("user_id")
    @get:PropertyName("user_id")
    var userId: String = "",
    @SerialName("variant_id")
    @set:PropertyName("variant_id")
    @get:PropertyName("variant_id")
    var variantId: String = "",
    @SerialName("variant_option_id")
    @set:PropertyName("variant_option_id")
    @get:PropertyName("variant_option_id")
    var variantOptionId: String = "",
    @SerialName("variant_name")
    @set:PropertyName("variant_name")
    @get:PropertyName("variant_name")
    var variantName: String = "",
    @SerialName("variant_value")
    @set:PropertyName("variant_value")
    @get:PropertyName("variant_value")
    var variantValue: String = "",
    @set:PropertyName("quantity")
    @get:PropertyName("quantity")
    var quantity: Int = 0,
    @set:PropertyName("price")
    @get:PropertyName("price")
    var price: String = "0"
)




