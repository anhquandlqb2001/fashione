package vn.quanprolazer.fashione.domain

import android.os.Parcelable
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(val userId: String, //Document ID is actually the user id
                val first: String,
                val last: String) : Parcelable {

    companion object {
        fun DocumentSnapshot.toUser(): User? {
            return try {
                val first = getString("first")!!
                val last = getString("last")!!
                User(id, first, last)
            } catch (e: Exception) {
                Log.e(TAG, "Error converting user profile", e)
                null
            }
        }
        private const val TAG = "User"
    }
}

@Parcelize
data class Category(val categoryId: String,
                val name: String) : Parcelable {

    companion object {
        fun DocumentSnapshot.toCategory(): Category? {
            return try {
                val name = getString("name")!!
                Log.i(TAG, name)
                Category(id, name)
            } catch (e: Exception) {
                Log.e(TAG, "Error converting category profile", e)
                null
            }
        }
        private const val TAG = "Category"
    }
}