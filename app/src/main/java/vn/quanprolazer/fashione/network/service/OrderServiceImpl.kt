/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network.service

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import vn.quanprolazer.fashione.domain.model.CartItem

class OrderServiceImpl : OrderService {
    override suspend fun addToCart(cartItem: CartItem, userId: String): Boolean {
        val db = FirebaseFirestore.getInstance()
        return try {
            val doc =
                db.collection("cart")
                    .whereEqualTo("user_id", userId)
                    .get()
                    .await()
                    .documents[0]

            Timber.i(doc.toString())
            true
        } catch (e: Exception) {
            Timber.e(e)
            false
        }
    }
}