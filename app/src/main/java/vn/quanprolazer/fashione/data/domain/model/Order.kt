/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.model

import com.google.firebase.firestore.Exclude
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Order(@SerialName("user_id") val userId: String,
                 @SerialName("address_id") val addressId: String,
                 @SerialName("shipping_price_total") val shippingPriceTotal: String,
                 @SerialName("product_price_total") val productPriceTotal: String,
                 @SerialName("created_at") val createdAt: String,
                 val status: OrderStatus,
                 @Exclude val id: String? = null
)

@Serializable
data class OrderItem(@SerialName("product_id") val productId: String,
                     @SerialName("variant_id") val variantId: String,
                     @SerialName("variant_option_id") val variantOptionId: String,
                     @SerialName("product_name") val productName: String,
                     @SerialName("variant_name") val variantName: String,
                     @SerialName("variant_value") val variantValue: String,
                     @SerialName("review_status") val reviewStatus: ReviewStatus,
                     val price: String,
                     val quantity: Int,
                     @SerialName("order_id") var orderId: String? = "",
                     @Exclude val id: String? = null,
                     @Exclude val cartItemId: String? = null
)

@Serializable
enum class OrderStatus(val status: String? = null) {
    @SerialName("CONFIRMING")
    CONFIRMING("Đang xác nhận"),

    @SerialName("DELIVERING")
    DELIVERING(("Đang vận chuyển")),

    @SerialName("DELIVERED")
    DELIVERED("Hoàn thành")
}