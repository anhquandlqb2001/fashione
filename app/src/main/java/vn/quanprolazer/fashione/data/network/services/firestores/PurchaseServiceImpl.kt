/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import vn.quanprolazer.fashione.data.network.models.NetworkOrder
import vn.quanprolazer.fashione.data.network.models.NetworkOrderItem
import vn.quanprolazer.fashione.domain.models.OrderStatus
import vn.quanprolazer.fashione.domain.models.Resource

class PurchaseServiceImpl : PurchaseService {
    override suspend fun getOrders(
        userId: String, status: OrderStatus,
    ): Resource<List<NetworkOrder>> {
        val db = FirebaseFirestore.getInstance()
        return try {
            val list = db.collection("orders").whereEqualTo("user_id", userId)
                .whereEqualTo("status", status.name).get().await().documents.mapNotNull {
                    it.toObject((NetworkOrder::class.java))
                }
            Resource.Success(list)
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }

    override suspend fun getOrderItems(orderId: String): Resource<List<NetworkOrderItem>> {
        val db = FirebaseFirestore.getInstance()
        return try {
            val list = db.collection("order_items").whereEqualTo("order_id", orderId).get()
                .await().documents.mapNotNull {
                    it.toObject((NetworkOrderItem::class.java))
                }
            Resource.Success(list)
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }
}