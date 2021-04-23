/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.mapper

import vn.quanprolazer.fashione.data.domain.model.AddToCartItem
import vn.quanprolazer.fashione.data.domain.model.CartItem
import vn.quanprolazer.fashione.data.mapper.Mapper

object CartItemMapper : Mapper<CartItem, AddToCartItem> {
    override fun map(input: CartItem): AddToCartItem {
        return AddToCartItem(
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