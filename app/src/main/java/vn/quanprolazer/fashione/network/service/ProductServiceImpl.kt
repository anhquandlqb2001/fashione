/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network.service

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import vn.quanprolazer.fashione.network.dto.*

object ProductServiceImpl : ProductService {
    override suspend fun getProducts(): List<NetworkProduct> {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("products")
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject((NetworkProduct::class.java)) }
        } catch (e: Exception) {
            Timber.e(e)
            listOf()
        }
    }

    override suspend fun getProductDetailByProductId(productId: String): NetworkProductDetail {
        val db = FirebaseFirestore.getInstance()

        return try {
            val list = db.collection("product_detail")
                .whereEqualTo("product_id", productId)
                .limit(1)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(NetworkProductDetail::class.java) }[0]

            Timber.i(list.toString())

            return list
        } catch (e: Exception) {
            Timber.e(e)
            NetworkProductDetail()
        }
    }

    override suspend fun getProductsByCategoryId(categoryId: String): List<NetworkProduct> {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("products")
                .whereEqualTo("category_id", categoryId)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(NetworkProduct::class.java) }
        } catch (e: Exception) {
            Timber.e(e)
            listOf()
        }
    }
}