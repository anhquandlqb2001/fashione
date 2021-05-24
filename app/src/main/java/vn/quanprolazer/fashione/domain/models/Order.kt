/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import vn.quanprolazer.fashione.data.network.models.NetworkOrder
import vn.quanprolazer.fashione.data.network.models.NetworkOrderItem
import vn.quanprolazer.fashione.data.network.models.NetworkOrderItemStatusType


data class Order(
    val userId: String,
    val addressId: String,
    val shippingPriceTotal: String,
    val productPriceTotal: String,
    val createdAt: String,
    val id: String? = null
)

internal fun Order.toDataModel() = NetworkOrder(
    userId = userId,
    addressId = addressId,
    shippingPriceTotal = shippingPriceTotal,
    productPriceTotal = productPriceTotal,
    createdAt = createdAt
)

data class OrderItem(
    val productId: String,
    val variantId: String,
    val variantOptionId: String,
    val productName: String,
    val variantName: String,
    val variantValue: String,
    val reviewStatus: ReviewStatus,
    val price: String,
    val quantity: Int,
    var orderId: String? = "",
    val id: String? = null,
    val cartItemId: String? = null
)

internal fun OrderItem.toDataModel() = NetworkOrderItem(
    productId = productId,
    variantId = variantId,
    variantOptionId = variantOptionId,
    productName = productName,
    variantName = variantName,
    variantValue = variantValue,
    price = price,
    quantity = quantity,
    orderId = orderId
)

@Serializable
enum class OrderItemStatusType(val status: String? = null) {
    @SerialName("CONFIRMING")
    CONFIRMING("Đang xác nhận"),

    @SerialName("COLLECTING")
    COLLECTING("Chờ lấy hàng"),

    @SerialName("DELIVERING")
    DELIVERING(("Đang vận chuyển")),

    @SerialName("DELIVERED")
    DELIVERED(("Đã giao")),

    @SerialName("COMPLETE")
    COMPLETE("Hoàn thành")
}

internal fun OrderItemStatusType.toDataModel() = NetworkOrderItemStatusType.valueOf(this.name)

data class DeliveryStatus(
    val status: OrderItemStatusType,
    val quantity: Int
)