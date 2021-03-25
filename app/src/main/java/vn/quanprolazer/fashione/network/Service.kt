package vn.quanprolazer.fashione.network

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import vn.quanprolazer.fashione.domain.Category
import vn.quanprolazer.fashione.domain.Category.Companion.toCategory
import vn.quanprolazer.fashione.domain.User
import vn.quanprolazer.fashione.domain.User.Companion.toUser


object FirebaseProfileService {
    private const val TAG = "FirebaseProfileService"
    suspend fun getProfileData(userId: String): User? {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("users")
                .document(userId).get().await().toUser()
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user details", e)
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