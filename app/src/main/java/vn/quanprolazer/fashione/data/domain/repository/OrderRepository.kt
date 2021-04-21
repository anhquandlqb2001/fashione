/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.repository

import vn.quanprolazer.fashione.data.domain.model.AddToCartItem
import vn.quanprolazer.fashione.data.domain.model.CartItem
import vn.quanprolazer.fashione.data.domain.model.Result

interface OrderRepository {

    suspend fun addToCart(addToCartItem: AddToCartItem): Result<Boolean>

    suspend fun getCartItems(): Result<List<CartItem>>

}