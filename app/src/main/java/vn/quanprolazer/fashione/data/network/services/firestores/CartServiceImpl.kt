/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import vn.quanprolazer.fashione.data.network.models.NetworkCartItem
import vn.quanprolazer.fashione.data.network.toHashMap
import vn.quanprolazer.fashione.domain.models.AddToCartItem
import vn.quanprolazer.fashione.domain.models.Resource

class CartServiceImpl : CartService {
    override suspend fun getCartItemCount(userId: String): Int {
        val db = FirebaseFirestore.getInstance()
        return db.collection("carts")
            .get()
            .await()
            .documents.size
    }

    /**
     * @param [documentId] if not null then this function for undo delete
     */
    override suspend fun addToCart(
        addToCartItem: AddToCartItem, userId: String, documentId: String?,
    ): Boolean {
        val db = FirebaseFirestore.getInstance()

        /**
         * Check if item has already exist in cart
         */
        val existCartItem = db.collection("carts").whereEqualTo(
            "variant_option_id", addToCartItem.variantOptionId
        ).get().await().documents
        /**
         * If item already exist => increase qty
         */
        if (existCartItem.size != 0) {
            val washingtonRef = db.collection("carts").document(existCartItem[0].id)
            washingtonRef.update(
                "quantity", FieldValue.increment(addToCartItem.quantity.toLong())
            )
            return true
        }

        /**
         * Undo delete cart item
         */
        documentId?.let {
            db.collection("carts").document(documentId).set(addToCartItem.toHashMap()).await()
            return true
        }

        /**
         * Normal add to cart
         */
        db.collection("carts").add(addToCartItem.toHashMap()).await()
        return true
    }

    override suspend fun getCartItems(userId: String): Resource<List<NetworkCartItem>> {
        val db = FirebaseFirestore.getInstance()
        return try {
            val response = db.collection("carts").whereEqualTo("user_id", userId).get()
                .await().documents.mapNotNull { it.toObject(NetworkCartItem::class.java) }

            Resource.Success(response)
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }

    override suspend fun updateCartItem(cartItemId: String, quantity: Int): Boolean {
        val db = FirebaseFirestore.getInstance()
        db.collection("carts").document(cartItemId).update("quantity", quantity).await()
        return true
    }

    override suspend fun removeCartItem(cartItemId: String): Boolean {
        val db = FirebaseFirestore.getInstance()
        db.collection("carts").document(cartItemId).delete().await()
        return true
    }

    override suspend fun removeCartItems(cartItemIds: List<String>): Boolean {
        val db = FirebaseFirestore.getInstance()
        val batch = db.batch()
        cartItemIds.forEach {
            val docRef =
                db.collection("carts").document(it) //automatically generate unique id
            batch.delete(docRef)
        }
        batch.commit().await()
        return true
    }
}