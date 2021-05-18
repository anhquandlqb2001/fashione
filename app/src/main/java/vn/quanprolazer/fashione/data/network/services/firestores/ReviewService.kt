/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import com.google.firebase.firestore.DocumentSnapshot
import vn.quanprolazer.fashione.data.network.models.NetworkRating
import vn.quanprolazer.fashione.data.network.models.NetworkReview
import vn.quanprolazer.fashione.data.network.models.NetworkReviewResponse
import vn.quanprolazer.fashione.domain.models.Resource

interface ReviewService {
    suspend fun addReview(review: NetworkReview): Resource<String>

    suspend fun addRating(rating: NetworkRating): Resource<Boolean>

    suspend fun getReviews(
        productId: String,
        lastVisible: DocumentSnapshot? = null
    ): Resource<NetworkReviewResponse>

    suspend fun getRating(reviewId: String): Resource<NetworkRating>

    suspend fun getRatings(productId: String): Resource<List<NetworkRating>>
}