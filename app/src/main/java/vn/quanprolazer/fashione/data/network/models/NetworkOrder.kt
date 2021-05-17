/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.models

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import vn.quanprolazer.fashione.domain.models.OrderStatus
import vn.quanprolazer.fashione.domain.models.ReviewStatus

data class NetworkOrder(
    @DocumentId val id: String = "",
    @set:PropertyName("user_id")
    @get:PropertyName("user_id")
    var userId: String = "",

    @set:PropertyName("address_id")
    @get:PropertyName("address_id")
    var addressId: String = "",

    @set:PropertyName("shipping_price_total")
    @get:PropertyName("shipping_price_total")
    var shippingPriceTotal: String = "",

    @set:PropertyName("product_price_total")
    @get:PropertyName("product_price_total")
    var productPriceTotal: String = "",

    val status: OrderStatus = OrderStatus.CONFIRMING,

    @set:PropertyName("created_at")
    @get:PropertyName("created_at")
    var createdAt: String = "",
)


data class NetworkOrderItem(
    @DocumentId val id: String = "",
    @set:PropertyName("product_id") @get:PropertyName("product_id") var productId: String = "",
    @set:PropertyName("variant_id") @get:PropertyName("variant_id") var variantId: String = "",
    @set:PropertyName("variant_option_id") @get:PropertyName("variant_option_id") var variantOptionId: String = "",
    @set:PropertyName("product_name") @get:PropertyName("product_name") var productName: String = "",
    @set:PropertyName("variant_name") @get:PropertyName("variant_name") var variantName: String = "",
    @set:PropertyName("variant_value") @get:PropertyName("variant_value") var variantValue: String = "",
    val price: String = "",
    val quantity: Int = -1,
    @set:PropertyName("order_id") @get:PropertyName("order_id") var orderId: String? = "",
    @set:PropertyName("review_status") @get:PropertyName("review_status") var reviewStatus: ReviewStatus = ReviewStatus.NO
)
