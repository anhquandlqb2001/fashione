/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network.service

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import vn.quanprolazer.fashione.network.dto.NetworkCategory

class CategoryServiceImpl : CategoryService {
    override suspend fun getCategoryList(source: Source): List<NetworkCategory> {
        val db = FirebaseFirestore.getInstance()
        return try {
                db.collection("categories")
                .get(source)
                .await()
                .documents
                .mapNotNull {
                    it.toObject((NetworkCategory::class.java))
                }
        } catch (e: Exception) {
            Timber.e(e)
            listOf()
        }
    }

}