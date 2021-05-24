/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import vn.quanprolazer.fashione.data.network.models.NetworkReviewStatus
import vn.quanprolazer.fashione.data.network.models.NetworkRating
import vn.quanprolazer.fashione.data.network.models.NetworkReviewFirestore
import vn.quanprolazer.fashione.domain.models.Resource

interface ReviewService {
    suspend fun addReview(review: NetworkReviewFirestore): String

    suspend fun addRating(rating: NetworkRating): Boolean

    suspend fun getRating(reviewId: String): NetworkRating?

    suspend fun getRatings(productId: String): List<NetworkRating>

    suspend fun checkUserWithThisItemHasReview(orderItemId: String): NetworkReviewStatus
}