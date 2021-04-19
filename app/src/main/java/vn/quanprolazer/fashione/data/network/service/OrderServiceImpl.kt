/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.service

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import vn.quanprolazer.fashione.data.domain.model.Result
import vn.quanprolazer.fashione.data.network.dto.NetworkCartItem
import vn.quanprolazer.fashione.data.network.mapper.toHashMap
class OrderServiceImpl : OrderService {
    override suspend fun addToCart(networkCartItem: NetworkCartItem, userId: String): Result<Boolean> {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("carts")
                .add(networkCartItem.toHashMap())
                .await()

            Result.Success(true)
        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(e)
        }
    }
}
