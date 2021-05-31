/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */


package vn.quanprolazer.fashione.data.network.services.firestores

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import vn.quanprolazer.fashione.data.network.models.NetworkCategory

class CategoryServiceImpl : CategoryService {
    override suspend fun getCategoryList() =
        FirebaseFirestore.getInstance().collection("categories")
            .get()
            .await()
            .documents
            .mapNotNull {
                it.toObject((NetworkCategory::class.java))
            }

}