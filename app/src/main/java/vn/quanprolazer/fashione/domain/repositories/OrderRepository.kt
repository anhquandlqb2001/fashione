/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.repositories

import vn.quanprolazer.fashione.domain.models.*

interface OrderRepository {

    suspend fun createOrder(order: Order, orderItems: List<OrderItem>): Resource<Boolean>

    suspend fun updateOrderReviewStatus(
        status: ReviewStatus,
        orderItemId: String
    ): Resource<Boolean>

    suspend fun getDeliveryStatus(): Resource<List<DeliveryStatus>>

}