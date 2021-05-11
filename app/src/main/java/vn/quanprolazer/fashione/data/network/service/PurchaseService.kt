/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.service

import vn.quanprolazer.fashione.data.domain.model.OrderStatus
import vn.quanprolazer.fashione.data.domain.model.Resource
import vn.quanprolazer.fashione.data.network.dto.NetworkOrder
import vn.quanprolazer.fashione.data.network.dto.NetworkOrderItem

interface PurchaseService {
    suspend fun getOrders(userId: String, status: OrderStatus): Resource<List<NetworkOrder>>

    suspend fun getOrderItems(orderId: String): Resource<List<NetworkOrderItem>>
}