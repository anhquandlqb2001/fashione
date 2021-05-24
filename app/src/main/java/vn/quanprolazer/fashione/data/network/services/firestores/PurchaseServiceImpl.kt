/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import vn.quanprolazer.fashione.data.network.models.NetworkOrderItem
import vn.quanprolazer.fashione.data.network.models.NetworkOrderItemStatus
import vn.quanprolazer.fashione.domain.models.OrderStatus

class PurchaseServiceImpl : PurchaseService {

    override suspend fun getOrderStatus(
        userId: String,
        status: OrderStatus
    ): List<NetworkOrderItemStatus> {
        val db = FirebaseFirestore.getInstance()
        return db.collection("order_item_statuses").whereEqualTo("user_id", userId)
            .whereEqualTo("status", status.name).get().await().documents.mapNotNull {
                it.toObject((NetworkOrderItemStatus::class.java))
            }
    }

    override suspend fun getOrderItems(orderId: List<String>): List<NetworkOrderItem> {
        val db = FirebaseFirestore.getInstance()
        return db.collection("order_items").whereIn(FieldPath.documentId(), orderId).get()
            .await().documents.mapNotNull {
                it.toObject((NetworkOrderItem::class.java))
            }
    }
}