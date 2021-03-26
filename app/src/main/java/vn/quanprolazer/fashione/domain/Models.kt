package vn.quanprolazer.fashione.domain

import android.os.Parcelable
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.parcelize.Parcelize


@Parcelize
data class Product(val productId: String, //Document ID is actually the user id
                val productName: String,
                val productImage: String,
                val productPrice: String) : Parcelable {

    companion object {
        fun DocumentSnapshot.toProduct(): Product? {
            return try {
                val productName = getString("productName")!!
                val productImage = getString("productImageUrl")!!
                val productPrice = getString("price")!!
                Product(id, productName, productImage, productPrice)
            } catch (e: Exception) {
                Log.e(TAG, "Error converting product", e)
                null
            }
        }
        private const val TAG = "Product"
    }
}

@Parcelize
data class Category(val categoryId: String,
                    val name: String) : Parcelable {

    companion object {
        fun DocumentSnapshot.toCategory(): Category? {
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