/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.service

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import vn.quanprolazer.fashione.data.domain.model.AddToCartItem
import vn.quanprolazer.fashione.data.domain.model.Resource
import vn.quanprolazer.fashione.data.network.dto.NetworkCartItem
import vn.quanprolazer.fashione.data.network.mapper.toHashMap


class OrderServiceImpl : OrderService {
    override suspend fun addToCart(addToCartItem: AddToCartItem, userId: String, documentId: String?): Resource<Boolean> {
        val db = FirebaseFirestore.getInstance()
        return try {
            val existCartItem = db.collection("carts").whereEqualTo(
                "variant_option_id", addToCartItem.variantOptionId
            ).get().await().documents

            // if cart item has exist
            if (existCartItem.size != 0) {
                val washingtonRef = db.collection("carts").document(existCartItem[0].id)
                washingtonRef.update(
                    "quantity", FieldValue.increment(addToCartItem.quantity.toLong())
                )
                return Resource.Success(true)
            }

            // if this is undo delete
            documentId?.let {
                db.collection("carts").document(documentId).set(addToCartItem.toHashMap()).await()
                return Resource.Success(true)
            }

            db.collection("carts").add(addToCartItem.toHashMap()).await()
            Resource.Success(true)
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
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

    override suspend fun updateCartItem(cartItemId: String, quantity: Int): Resource<Boolean> {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("carts").document(cartItemId).update("quantity", quantity).await()
            Resource.Success(true)
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }

    override suspend fun removeCartItem(cartItemId: String): Resource<Boolean> {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("carts").document(cartItemId).delete().await()
            Resource.Success(true)
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }
}

