package vn.quanprolazer.fashione.network

import android.util.Log
import com.algolia.search.saas.RequestOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await
import org.json.JSONObject
import vn.quanprolazer.fashione.domain.Category
import vn.quanprolazer.fashione.domain.Category.Companion.toCategory
import vn.quanprolazer.fashione.domain.Product
import vn.quanprolazer.fashione.domain.Product.Companion.toProduct
import vn.quanprolazer.fashione.network.Algolia.productIndex

object FashioneProductService {
    private const val TAG = "FashioneProductService"
    suspend fun getProducts(): List<Product>? {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("products")
                .get()
                .await()
                .documents
                .mapNotNull { it.toProduct() }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting list products", e)
            null
        }
    }

    suspend fun getProductsByCategoryId(categoryId: String): List<Product>? {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("products")
                .whereEqualTo("categoryId", categoryId)
                .get()
                .await()
                .documents
                .mapNotNull { it.toProduct() }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting list products", e)
            null
        }
    }

    suspend fun searchProductByQuery(query: String): List<Product>? {
        val db = FirebaseFirestore.getInstance()

//        val algoliaQuery = com.algolia.search.saas.Query()
//
//        algoliaQuery.set("productName", query)
//
//        val result = productIndex.search(algoliaQuery, RequestOptions())
//
//        Log.i("Service", result.toString())

        return try {
            db.collection("products")
                .orderBy("productName")
                .startAt(query)
                .endAt("$query\uf8ff")
                .get()
                .await()
                .documents
                .mapNotNull { it.toProduct() }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting list products", e)
            null
        }
    }
}


object FashioneCategoryService {
    private const val TAG = "FashioneCategoryService"
    suspend fun getCategories(): List<Category>? {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("categories").get().await().documents.mapNotNull { it.toCategory() }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user details", e)
            null
        }
    }
}

//object FashioneProductAdminService {
//    private const val TAG = "FashioneAdminService"
//    suspend fun addProduct(product: Product) : Boolean {
//        return try {
//
//            val db = FirebaseFirestore.getInstance()
//            db.collection("products")
//                .add(product)
//                .addOnSuccessListener {
//                    productIndex.addObjectAsync(JSONObject(Gson().toJson(it)), null)
//                }
//            true
//        } catch (e: Exception) {
//            Log.e(TAG, "Error getting user details", e)
//            false
//        }
//
//    }
//}