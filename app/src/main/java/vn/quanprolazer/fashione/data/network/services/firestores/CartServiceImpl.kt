/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CartServiceImpl : CartService {
    override suspend fun getCartItemCount(userId: String): Int {
        val db = FirebaseFirestore.getInstance()
        return db.collection("carts")
            .get()
            .await()
            .documents.size
    }
}