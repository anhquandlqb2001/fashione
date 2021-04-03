/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network.service

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import vn.quanprolazer.fashione.domain.model.Result
import vn.quanprolazer.fashione.domain.model.SimpleResult
import vn.quanprolazer.fashione.network.dto.NetworkCategory

class CategoryServiceImpl : CategoryService {
    override suspend fun getCategoryList(): SimpleResult<List<NetworkCategory>> {
        val db = FirebaseFirestore.getInstance()
        return try {
            val docs = db.collection("categories")
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject((NetworkCategory::class.java)) }

            Result.Success(docs)
        } catch (e: Exception) {
            Timber.e(e)
            Result.Failure(e)
        }
    }
}