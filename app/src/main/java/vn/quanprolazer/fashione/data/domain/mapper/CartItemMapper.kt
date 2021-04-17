/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.mapper

import vn.quanprolazer.fashione.data.domain.model.CartItem
import vn.quanprolazer.fashione.data.mapper.Mapper
import vn.quanprolazer.fashione.data.network.dto.NetworkCartItem

object CartItemMapper : Mapper<CartItem, NetworkCartItem> {
    override fun map(input: CartItem): NetworkCartItem {
        return NetworkCartItem(
            input.productId,
            input.userId,
            input.variantId,
            input.variantOptionId,
            input.variantName,
            input.variantValue,
            input.quantity,
            input.price
        )
    }
}