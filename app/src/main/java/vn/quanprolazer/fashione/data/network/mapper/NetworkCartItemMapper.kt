/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.mapper

import vn.quanprolazer.fashione.data.domain.model.CartItem
import vn.quanprolazer.fashione.data.mapper.ListMapper
import vn.quanprolazer.fashione.data.network.dto.NetworkCartItem


object NetworkCartItemMapper : ListMapper<NetworkCartItem, CartItem> {
    override fun map(input: List<NetworkCartItem>): List<CartItem> {
        return input.map {
            CartItem(
                it.id,
                it.productId,
                it.userId,
                it.variantId,
                it.variantOptionId,
                it.variantName,
                it.variantValue,
                it.quantity,
                it.price
            )
        }
    }
}