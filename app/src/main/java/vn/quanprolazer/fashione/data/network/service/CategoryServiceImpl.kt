/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.service

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import vn.quanprolazer.fashione.data.network.dto.NetworkCategory
import vn.quanprolazer.fashione.data.domain.model.Result

class CategoryServiceImpl : CategoryService {
    override suspend fun getCategoryList(source: Source): Result<List<NetworkCategory>> {
        val db = FirebaseFirestore.getInstance()
        return try {
            val list = db.collection("categories")
                .get(source)
                .await()
                .documents
                .mapNotNull {
                    it.toObject((NetworkCategory::class.java))
                }

            return Result.Success(list)
        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(e)
        }
    }
}