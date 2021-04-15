/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network.service

import vn.quanprolazer.fashione.domain.model.CartItem

interface OrderService {

    suspend fun addToCart(cartItem: CartItem, userId: String): Boolean

}