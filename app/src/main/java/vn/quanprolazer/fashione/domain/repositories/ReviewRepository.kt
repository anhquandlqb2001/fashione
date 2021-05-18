/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.repositories

import com.google.firebase.firestore.DocumentSnapshot
import vn.quanprolazer.fashione.domain.models.Rating
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.models.Review
import vn.quanprolazer.fashione.domain.models.ReviewWithRatingResponse

interface ReviewRepository {
    suspend fun addReview(review: Review, rating: Rating): Resource<Boolean>

    suspend fun getReviewWithRating(
        productId: String,
        lastVisible: DocumentSnapshot? = null
    ): Resource<ReviewWithRatingResponse>

    suspend fun getRatings(productId: String): Resource<List<Rating>>

}