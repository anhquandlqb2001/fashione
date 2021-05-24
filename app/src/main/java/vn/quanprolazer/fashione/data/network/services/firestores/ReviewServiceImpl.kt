/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import vn.quanprolazer.fashione.data.network.models.NetworkReviewStatus
import vn.quanprolazer.fashione.data.network.models.NetworkRating
import vn.quanprolazer.fashione.data.network.models.NetworkReviewFirestore
import vn.quanprolazer.fashione.data.network.toHashMap
import vn.quanprolazer.fashione.domain.models.Resource

class ReviewServiceImpl : ReviewService {

    companion object {
        private const val PER_PAGE = 10
    }

    override suspend fun addReview(review: NetworkReviewFirestore): Resource<String> {
        val db = FirebaseFirestore.getInstance()
        return try {
            val response =
                db.collection("reviews").add(review.toHashMap())
                    .await()

            Resource.Success(response.id)
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }

    override suspend fun addRating(rating: NetworkRating): Resource<Boolean> {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("review_ratings").add(rating.toHashMap())
                .await()

            Resource.Success(true)
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }

    override suspend fun getRating(reviewId: String): Resource<NetworkRating> {
        val db = FirebaseFirestore.getInstance()
        return try {
            val response =
                db.collection("review_ratings").whereEqualTo("review_id", reviewId).get()
                    .await().documents.mapNotNull { it.toObject(NetworkRating::class.java) }

            if (response.isEmpty()) return Resource.Error(Exception("Review rating not found"))

            Resource.Success(response[0])
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }

    override suspend fun getRatings(productId: String): Resource<List<NetworkRating>> {
        val db = FirebaseFirestore.getInstance()
        return try {
            val response =
                db.collection("review_ratings").get()
                    .await().documents.mapNotNull { it.toObject(NetworkRating::class.java) }

            if (response.isEmpty()) return Resource.Success(listOf())

            Resource.Success(response)
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
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