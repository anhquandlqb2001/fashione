/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.mapper

import vn.quanprolazer.fashione.data.domain.model.CheckoutItem
import vn.quanprolazer.fashione.data.domain.model.OrderData
import vn.quanprolazer.fashione.data.domain.model.Resource

object OrderDataMapper {
    fun map(orderData: OrderData): List<CheckoutItem> = orderData.items.map {
        CheckoutItem(
            it.id,
            it.productId,
            it.userId,
            it.variantId,
            it.variantOptionId,
            (it.product as Resource.Success).data.name,
            it.variantValue,
            it.variantName,
            it.quantity,
            it.price,
            (it.cartItemImg as Resource.Success).data.url
        )
    }
}
