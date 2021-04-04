/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network.service

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import vn.quanprolazer.fashione.network.dto.NetworkCategory

class CategoryServiceImpl : CategoryService {
    override suspend fun getCategoryList(): List<NetworkCategory> {
        val db = FirebaseFirestore.getInstance()
        return try {
            return db.collection("categories")
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject((NetworkCategory::class.java)) }
        } catch (e: Exception) {
            Timber.e(e)
            listOf()
        }
    }
}