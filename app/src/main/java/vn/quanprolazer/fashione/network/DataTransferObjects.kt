package vn.quanprolazer.fashione.network

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import vn.quanprolazer.fashione.domain.Category
import vn.quanprolazer.fashione.domain.Product


/**
 * Product get from cloud
 */
data class NetworkProduct(
    val categoryId: String,
    val name: String,
    val imgUrl: String,
    val price: String
) {
    companion object {
        /**
         * Convert network result to domain object
         */
        fun DocumentSnapshot.asDomainProduct(): Product? {
            return try {
                val productName = getString("name")!!
                val productImageUrl = getString("imgUrl")!!
                val productPrice = getString("price")!!
                val categoryId = getString("categoryId")!!
                Product(id, categoryId, productName, productImageUrl, productPrice)
            } catch (e: Exception) {
                Log.e(TAG, "Error converting product", e)
                null
            }
        }

        private const val TAG = "Product"
    }
}

/**
 * Category get from cloud
 */
data class NetworkCategory(
    val id: String,
    val name: String
) {
    companion object {
        /**
         * Convert network result to domain object
         */
        fun DocumentSnapshot.asDomainCategory(): Category? {
            return try {
                val name = requireNotNull(getString("categoryName"))
                Category(id, name)
            } catch (e: Exception) {
                Log.e(TAG, "Error converting category profile", e)
                null
            }
        }
        private const val TAG = "Category"
    }
}