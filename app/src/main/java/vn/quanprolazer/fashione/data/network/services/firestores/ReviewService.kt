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
    suspend fun addReview(review: NetworkReviewFirestore): Resource<String>

    suspend fun addRating(rating: NetworkRating): Resource<Boolean>

    suspend fun getRating(reviewId: String): Resource<NetworkRating>

    suspend fun getRatings(productId: String): Resource<List<NetworkRating>>

    suspend fun checkUserWithThisItemHasReview(orderItemId: String): NetworkReviewStatus
}