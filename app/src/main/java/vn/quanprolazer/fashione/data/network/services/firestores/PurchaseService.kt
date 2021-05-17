/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import vn.quanprolazer.fashione.data.network.models.NetworkOrder
import vn.quanprolazer.fashione.data.network.models.NetworkOrderItem
import vn.quanprolazer.fashione.domain.models.OrderStatus
import vn.quanprolazer.fashione.domain.models.Resource

interface PurchaseService {
    suspend fun getOrders(userId: String, status: OrderStatus): Resource<List<NetworkOrder>>

    suspend fun getOrderItems(orderId: String): Resource<List<NetworkOrderItem>>
}