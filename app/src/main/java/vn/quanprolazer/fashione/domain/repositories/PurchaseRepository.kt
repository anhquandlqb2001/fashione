/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.repositories

import vn.quanprolazer.fashione.domain.models.OrderItemStatusType
import vn.quanprolazer.fashione.domain.models.Purchase
import vn.quanprolazer.fashione.domain.models.Resource

interface PurchaseRepository {
    suspend fun getPurchaseItems(orderStatus: OrderItemStatusType): Resource<MutableList<Purchase>>
}