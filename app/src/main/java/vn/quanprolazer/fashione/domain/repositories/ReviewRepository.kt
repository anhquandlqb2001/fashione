/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.repositories

import vn.quanprolazer.fashione.domain.models.Rating
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.models.Review
import vn.quanprolazer.fashione.domain.models.ReviewRetrofitResponse

interface ReviewRepository {
    suspend fun addReview(review: Review, rating: Rating): Resource<Boolean>

    suspend fun getRatings(productId: String): Resource<List<Rating>>

    suspend fun getReviews(productId: String, lastVisibleId: String? = null): Resource<ReviewRetrofitResponse>

}