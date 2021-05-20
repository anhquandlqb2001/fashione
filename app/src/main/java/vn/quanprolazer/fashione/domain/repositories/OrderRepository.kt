/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.repositories

import vn.quanprolazer.fashione.domain.models.*

interface OrderRepository {

    suspend fun addToCart(addToCartItem: AddToCartItem): Resource<Boolean>

    suspend fun undoDeleteCartItem(cartItem: CartItem): Resource<Boolean>

    suspend fun updateCartItem(cartItemId: String, quantity: Int): Resource<Boolean>

    suspend fun getCartItems(): Resource<MutableList<CartItem>>

    suspend fun removeCartItem(cartItemId: String): Resource<Boolean>

    suspend fun createOrder(order: Order, orderItems: List<OrderItem>): Resource<Boolean>

    suspend fun updateOrderReviewStatus(
        status: ReviewStatus,
        orderItemId: String
    ): Resource<Boolean>

    suspend fun getDeliveryStatus(): Resource<List<DeliveryStatus>>

}