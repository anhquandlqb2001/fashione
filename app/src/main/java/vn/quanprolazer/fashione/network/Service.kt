/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network

import android.util.Log
import com.algolia.search.saas.Query
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.json.JSONObject
import vn.quanprolazer.fashione.domain.model.Category
import vn.quanprolazer.fashione.network.Algolia.productIndex
//import vn.quanprolazer.fashione.network.NetworkCategory.Companion.asDomainCategory
//import vn.quanprolazer.fashione.network.NetworkProduct.Companion.asDomainProduct
//import vn.quanprolazer.fashione.network.NetworkProductDetail.Companion.asDomainProductDetail

//object FashioneProductService {
//    private const val TAG = "FashioneProductService"
//
//    suspend fun getProducts(): List<Product>? {
//        val db = FirebaseFirestore.getInstance()
//        return try {
//            db.collection("products")
//                .get()
//                .await()
//                .documents
//                .mapNotNull { it.asDomainProduct() }
//        } catch (e: Exception) {
//            Log.e(TAG, "Error getting list products", e)
//            null
//        }
//    }
//
//    suspend fun getProductsByCategoryId(categoryId: String): List<Product>? {
//        val db = FirebaseFirestore.getInstance()
//        return try {
//            db.collection("products")
//                .whereEqualTo("category_id", categoryId)
//                .get()
//                .await()
//                .documents
//                .mapNotNull { it.asDomainProduct() }
//        } catch (e: Exception) {
//            Log.e(TAG, "Error getting list products", e)
//            null
//        }
//    }
//
//    suspend fun getProductDetailByProductId(productId: String) : ProductDetail? {
//        val db = FirebaseFirestore.getInstance()
//        return try {
//            db.collection("product_detail")
//                .whereEqualTo("product_id", productId)
//                .limit(1)
//                .get()
//                .await()
//                .documents
//                .mapNotNull { it.asDomainProductDetail() }[0]
//        } catch (e: Exception) {
//            Log.e(TAG, "Error getting list products", e)
//            null
//        }
//    }
//}

//object FashioneCategoryService {
//    private const val TAG = "FashioneCategoryService"
//    suspend fun getCategories(): List<Category>? {
//        val db = FirebaseFirestore.getInstance()
//        return try {
//            db.collection("categories")
//                .get()
//                .await()
//                .documents
//                .mapNotNull { it.asDomainCategory() }
//        } catch (e: Exception) {
//            Log.e(TAG, "Error getting user details", e)
//            null
//        }
//    }
//}


object Searcher : CoroutineScope {
    override val coroutineContext = Job()
    suspend fun search(query: String): JSONObject? {
        return withContext(Dispatchers.Default) {
            productIndex.search(Query(query), null)
        }
    }
}