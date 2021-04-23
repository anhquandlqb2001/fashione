/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.service

import vn.quanprolazer.fashione.data.domain.model.AddToCartItem
import vn.quanprolazer.fashione.data.domain.model.Result
import vn.quanprolazer.fashione.data.network.dto.NetworkCartItem

interface OrderService {

    suspend fun addToCart(addToCartItem: AddToCartItem, userId: String): Result<Boolean>

    suspend fun updateCartItem(cartItemId: String, quantity: Int): Result<Boolean>

    suspend fun getCartItems(userId: String): Result<List<NetworkCartItem>>
}