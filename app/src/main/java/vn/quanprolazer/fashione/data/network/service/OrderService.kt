/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.service

import vn.quanprolazer.fashione.data.domain.model.*
import vn.quanprolazer.fashione.data.network.dto.NetworkCartItem

interface OrderService {

    suspend fun addToCart(
        addToCartItem: AddToCartItem,
        userId: String,
        documentId: String? = null
    ): Resource<Boolean>

    suspend fun updateCartItem(cartItemId: String, quantity: Int): Resource<Boolean>

    suspend fun getCartItems(userId: String): Resource<List<NetworkCartItem>>

    suspend fun removeCartItem(cartItemId: String): Resource<Boolean>

    suspend fun removeCartItems(cartItemIds: List<String>): Resource<Boolean>

    suspend fun createOrder(order: Order): Resource<String>

    suspend fun createOrderItem(orderItems: List<OrderItem>): Resource<Boolean>
}