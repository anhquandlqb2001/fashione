/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.repositories

import kotlinx.coroutines.flow.Flow
import vn.quanprolazer.fashione.domain.models.AddToCartItem
import vn.quanprolazer.fashione.domain.models.CartItem
import vn.quanprolazer.fashione.domain.models.Resource

interface CartRepository {
    suspend fun addToCart(addToCartItem: AddToCartItem): Resource<Boolean>

    suspend fun undoDeleteCartItem(cartItem: CartItem): Resource<Boolean>

    suspend fun updateCartItem(cartItemId: String, quantity: Int): Resource<Boolean>

    suspend fun getCartItems(): Resource<MutableList<CartItem>>

    suspend fun removeCartItem(cartItemId: String): Resource<Boolean>

    suspend fun getCartItemCount(): Flow<Resource<Int>>
}