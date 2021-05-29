/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.models.ReviewStatus


class OrderServiceImpl : OrderService {

    override suspend fun updateOrderReviewStatus(
        reviewStatus: ReviewStatus,
        orderItemId: String
    ): Resource<Boolean> {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("order_items").document(orderItemId)
                .update("review_status", reviewStatus.name)
                .await()
            Resource.Success(true)
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }
}

