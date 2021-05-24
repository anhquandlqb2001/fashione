/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import vn.quanprolazer.fashione.data.network.models.NetworkOrderItem
import vn.quanprolazer.fashione.data.network.models.NetworkOrderItemStatus
import vn.quanprolazer.fashione.domain.models.OrderStatus

interface PurchaseService {

    suspend fun getOrderStatus(userId: String, status: OrderStatus): List<NetworkOrderItemStatus>

    suspend fun getOrderItems(orderId: List<String>): List<NetworkOrderItem>
}