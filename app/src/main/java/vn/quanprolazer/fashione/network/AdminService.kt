/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.Json
import org.json.JSONObject
//import vn.quanprolazer.fashione.network.NetworkProduct.Companion.asNetworkProduct
//
//object FashioneProductAdminService {
//    private const val TAG = "FashioneAdminService"
//    suspend fun addProduct(product: NetworkProduct) : Boolean {
//        val db = FirebaseFirestore.getInstance()
//        return try {
//            db.collection("products")
//                .add(product)
//                .await()
//                .addSnapshotListener { data, _ ->
//                    data?.let {
//                        // add product to Algolia db
//                        Algolia.productIndex.addObjectAsync(JSONObject(Json.encodeToString(NetworkProduct.serializer(), data.asNetworkProduct()!!)), null)
//                    }
//                }
//            true
//        } catch (e: Exception) {
//            Log.e(TAG, "Error getting user details", e)
//            false
//        }
//
//    }
//}
