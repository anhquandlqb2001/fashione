/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import vn.quanprolazer.fashione.domain.models.DeliveryStatus
import vn.quanprolazer.fashione.domain.models.OrderItemStatusType

@Serializable
data class NetworkOrder(
    @Exclude
    @DocumentId val id: String = "",
    @set:PropertyName("user_id")
    @get:PropertyName("user_id")
    @SerialName("user_id")
    var userId: String = "",

    @set:PropertyName("address_id")
    @get:PropertyName("address_id")
    @SerialName("address_id")
    var addressId: String = "",

    @set:PropertyName("shipping_price_total")
    @get:PropertyName("shipping_price_total")
    @SerialName("shipping_price_total")
    var shippingPriceTotal: String = "",

    @set:PropertyName("product_price_total")
    @get:PropertyName("product_price_total")
    @SerialName("product_price_total")
    var productPriceTotal: String = "",

    @set:PropertyName("created_at")
    @get:PropertyName("created_at")
    @SerialName("created_at")
    var createdAt: String = "",
)

@Serializable
data class NetworkOrderItem(
    @Exclude
    @DocumentId val id: String = "",
    @set:PropertyName("product_id")
    @get:PropertyName("product_id")
    @SerialName("product_id")
    var productId: String = "",
    @set:PropertyName("variant_id")
    @get:PropertyName("variant_id")
    @SerialName("variant_id")
    var variantId: String = "",
    @set:PropertyName("variant_option_id")
    @get:PropertyName("variant_option_id")
    @SerialName("variant_option_id")
    var variantOptionId: String = "",
    @set:PropertyName("product_name")
    @get:PropertyName("product_name")
    @SerialName("product_name")
    var productName: String = "",
    @set:PropertyName("variant_name")
    @get:PropertyName("variant_name")
    @SerialName("variant_name")
    var variantName: String = "",
    @set:PropertyName("variant_value")
    @get:PropertyName("variant_value")
    @SerialName("variant_value")
    var variantValue: String = "",
    val price: String = "",
    val quantity: Int = -1,
    @set:PropertyName("order_id")
    @get:PropertyName("order_id")
    @SerialName("order_id")
    @Exclude
    var orderId: String? = "",
    @set:PropertyName("review_status")
    @get:PropertyName("review_status")
    @SerialName("review_status")
    var reviewStatus: NetworkOrderItemReviewStatus = NetworkOrderItemReviewStatus.NO,

    @SerialName("user_id")
    @set:PropertyName("user_id")
    @get:PropertyName("user_id")
    var userId: String = "",
)


@Serializable
enum class NetworkOrderItemReviewStatus {
    YES,
    NO
}

@Serializable
data class NetworkDeliveryStatus(
    val status: OrderItemStatusType,
    val quantity: Int
)

internal fun NetworkDeliveryStatus.toDomainModel() = DeliveryStatus(status, quantity)

@Serializable
enum class NetworkOrderItemStatusType {
    @SerialName("CONFIRMING")
    CONFIRMING,

    @SerialName("COLLECTING")
    COLLECTING,

    @SerialName("DELIVERING")
    DELIVERING,

    @SerialName("DELIVERED")
    DELIVERED,

    @SerialName("COMPLETE")
    COMPLETE
}

data class NetworkOrderItemStatus(
    @Exclude
    @DocumentId
    val id: String = "",
    @set:PropertyName("order_id")
    @get:PropertyName("order_id")
    var orderId: String = "",
    @set:PropertyName("order_item_id")
    @get:PropertyName("order_item_id")
    var orderItemId: String = "",
    val status: NetworkOrderItemStatusType = NetworkOrderItemStatusType.CONFIRMING,

    @set:PropertyName("user_id")
    @get:PropertyName("user_id")
    var userId: String = "",

    @set:PropertyName("created_at")
    @get:PropertyName("created_at")
    var createdAt: Timestamp = Timestamp.now()
)

@Serializable
data class NetworkOrderStatus(
    @Exclude
    @DocumentId
    val id: String = "",
    @SerialName("order_id")
    @set:PropertyName("order_id")
    @get:PropertyName("order_id")
    var orderId: String = "",
    @SerialName("order_item_id")
    @set:PropertyName("order_item_id")
    @get:PropertyName("order_item_id")
    var orderItemId: String = "",
    @SerialName("current_order_item_status_id")
    @set:PropertyName("current_order_item_status_id")
    @get:PropertyName("current_order_item_status_id")
    var currentOrderItemStatusId: String = "",

    @SerialName("user_id")
    @set:PropertyName("user_id")
    @get:PropertyName("user_id")
    var userId: String = "",
)


@Serializable
data class CreateOrderRequest(
    val order: NetworkOrder,
    @SerialName("order_items")
    val orderItems: List<NetworkOrderItem>
)