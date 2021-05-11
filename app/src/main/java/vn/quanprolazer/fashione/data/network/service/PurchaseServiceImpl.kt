/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.service

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import vn.quanprolazer.fashione.data.domain.model.OrderStatus
import vn.quanprolazer.fashione.data.domain.model.Resource
import vn.quanprolazer.fashione.data.network.dto.NetworkOrder
import vn.quanprolazer.fashione.data.network.dto.NetworkOrderItem

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