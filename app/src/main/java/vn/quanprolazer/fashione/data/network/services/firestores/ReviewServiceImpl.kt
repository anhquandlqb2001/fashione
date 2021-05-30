/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import vn.quanprolazer.fashione.data.network.models.NetworkRating
import vn.quanprolazer.fashione.data.network.models.NetworkReviewFirestore
import vn.quanprolazer.fashione.data.network.models.NetworkReviewStatus
import vn.quanprolazer.fashione.data.network.toHashMap

class ReviewServiceImpl : ReviewService {

    companion object {
        private const val PER_PAGE = 10
    }

    override suspend fun addReview(review: NetworkReviewFirestore): String {
        val db = FirebaseFirestore.getInstance()
        val response =
            db.collection("reviews")
                .add(review.copy(createdAt = Timestamp.now().toDate().toString()).toHashMap())
                .await()

        return response.id
    }

    override suspend fun addRating(rating: NetworkRating): Boolean {
        val db = FirebaseFirestore.getInstance()
        db.collection("review_ratings").add(rating.toHashMap())
            .await()

        return true
    }

    override suspend fun getRating(reviewId: String): NetworkRating? {
        val db = FirebaseFirestore.getInstance()
        val response =
            db.collection("review_ratings").whereEqualTo("review_id", reviewId).get()
                .await().documents.mapNotNull { it.toObject(NetworkRating::class.java) }

        if (response.isEmpty()) return null

        return response[0]
    }

    override suspend fun getRatings(productId: String): List<NetworkRating> {
        val db = FirebaseFirestore.getInstance()
        val response =
            db.collection("review_ratings").whereEqualTo("product_id", productId).get()
                .await().documents.mapNotNull { it.toObject(NetworkRating::class.java) }

        if (response.isEmpty()) return listOf()

        return response
    }

    override suspend fun checkUserWithThisItemHasReview(orderItemId: String): NetworkReviewStatus {
        val db = FirebaseFirestore.getInstance()
        val response =
            db.collection("reviews").whereEqualTo("order_item_id", orderItemId).limit(1).get()
                .await().documents

        if (response.isEmpty()) return NetworkReviewStatus.NO
        return NetworkReviewStatus.YES
    }
}