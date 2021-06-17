/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.models

import android.content.res.Resources
import androidx.core.content.ContextCompat
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import vn.quanprolazer.fashione.FashioneApplication
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.data.network.models.NetworkOrder
import vn.quanprolazer.fashione.data.network.models.NetworkOrderItem
import vn.quanprolazer.fashione.data.network.models.NetworkOrderItemStatusType
import vn.quanprolazer.fashione.helpers.Strings


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
    CONFIRMING(Strings.get(R.string.text_confirming)),

    @SerialName("COLLECTING")
    COLLECTING(Strings.get(R.string.text_collecting)),

    @SerialName("DELIVERING")
    DELIVERING(Strings.get(R.string.text_delivering)),

    @SerialName("DELIVERED")
    DELIVERED(Strings.get(R.string.text_delivered)),

    @SerialName("COMPLETE")
    COMPLETE(Strings.get(R.string.text_completed))
}

internal fun OrderItemStatusType.toDataModel() = NetworkOrderItemStatusType.valueOf(this.name)

data class DeliveryStatus(
    val status: OrderItemStatusType,
    val quantity: Int
)