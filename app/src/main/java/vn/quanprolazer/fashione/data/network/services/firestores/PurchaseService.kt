/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import vn.quanprolazer.fashione.data.network.models.NetworkOrderItem
import vn.quanprolazer.fashione.data.network.models.NetworkOrderItemStatus
import vn.quanprolazer.fashione.data.network.models.NetworkOrderItemStatusType
import vn.quanprolazer.fashione.data.network.models.NetworkOrderStatus

interface PurchaseService {

    suspend fun getOrderStatus(userId: String): List<NetworkOrderStatus>

    suspend fun getOrderItemStatus(currentOrderItemStatusIds: List<String>, status: NetworkOrderItemStatusType): List<NetworkOrderItemStatus>

    suspend fun getOrderItems(orderItemIds: List<String>): List<NetworkOrderItem>
}