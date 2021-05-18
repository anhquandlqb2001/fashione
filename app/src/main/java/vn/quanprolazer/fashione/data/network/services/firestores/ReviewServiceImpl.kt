/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import vn.quanprolazer.fashione.data.network.models.NetworkRating
import vn.quanprolazer.fashione.data.network.models.NetworkReview
import vn.quanprolazer.fashione.data.network.models.NetworkReviewResponse
import vn.quanprolazer.fashione.data.network.toHashMap
import vn.quanprolazer.fashione.domain.models.Resource

class ReviewServiceImpl : ReviewService {

    companion object {
        private const val PER_PAGE = 10
    }

    override suspend fun addReview(review: NetworkReview): Resource<String> {
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

    override suspend fun getReviews(
        productId: String,
        lastVisible: DocumentSnapshot?
    ): Resource<NetworkReviewResponse> {
        val db = FirebaseFirestore.getInstance()
        return try {
            if (lastVisible == null) {
                val documents = db.collection("reviews")
                    .limit(PER_PAGE.toLong()).get().await().documents

                if (documents.isEmpty()) return Resource.Success(NetworkReviewResponse(listOf()))

                val _lastVisible = documents[documents.size - 1]

                return Resource.Success(NetworkReviewResponse(documents.mapNotNull {
                    it.toObject(
                        NetworkReview::class.java
                    )
                }, _lastVisible))
            }

            // Construct a new query starting at this document,
            // get the next 25 cities.
            val next = db.collection("reviews")
                .orderBy("createdAt")
                .startAfter(lastVisible)
                .limit(PER_PAGE.toLong()).get().await().documents

            if (next.isEmpty()) return Resource.Success(NetworkReviewResponse(listOf()))

            val _lastVisible = next[next.size - 1]

            Resource.Success(
                NetworkReviewResponse(
                    next.mapNotNull { it.toObject(NetworkReview::class.java) },
                    _lastVisible
                )
            )
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
}