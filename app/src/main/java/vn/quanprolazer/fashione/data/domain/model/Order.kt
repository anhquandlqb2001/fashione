/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Order(@SerialName("user_id") val userId: String,
                 @SerialName("address_id") val addressId: String,
                 @SerialName("shipping_price_total") val shippingPriceTotal: String,
                 @SerialName("product_price_total") val productPriceTotal: String,
                 @SerialName("created_at") val createdAt: String
)

@Serializable
data class OrderItem(@SerialName("product_variant_id") val productVariantId: String,
                     val price: String,
                     val quantity: Int,
                     @SerialName("order_id") var orderId: String? = ""
)