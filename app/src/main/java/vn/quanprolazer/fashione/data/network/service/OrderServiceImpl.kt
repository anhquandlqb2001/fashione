/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.service

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import timber.log.Timber
import vn.quanprolazer.fashione.data.domain.model.CartItem
import vn.quanprolazer.fashione.data.domain.model.Result

class OrderServiceImpl : OrderService {
    override suspend fun addToCart(cartItem: CartItem, userId: String): Result<Boolean> {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("carts")
                .add(Json.encodeToJsonElement(cartItem).toString())
                .await()

            Result.Success(true)
        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(e)
        }
    }
}
