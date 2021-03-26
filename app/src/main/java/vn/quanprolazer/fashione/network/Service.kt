package vn.quanprolazer.fashione.network

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import vn.quanprolazer.fashione.domain.Category
import vn.quanprolazer.fashione.domain.Category.Companion.toCategory
import vn.quanprolazer.fashione.domain.Product
import vn.quanprolazer.fashione.domain.Product.Companion.toProduct

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
