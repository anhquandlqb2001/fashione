/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.repository

import vn.quanprolazer.fashione.data.domain.model.AddToCartItem
import vn.quanprolazer.fashione.data.domain.model.CartItem
import vn.quanprolazer.fashione.data.domain.model.Resource

interface OrderRepository {

    suspend fun addToCart(addToCartItem: AddToCartItem): Resource<Boolean>

    suspend fun undoDeleteCartItem(cartItem: CartItem): Resource<Boolean>

    suspend fun updateCartItem(cartItemId: String, quantity: Int): Resource<Boolean>

    suspend fun getCartItems(): Resource<MutableList<CartItem>>

    suspend fun removeCartItem(cartItemId: String) : Resource<Boolean>
}