/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.repositories

import com.google.firebase.firestore.DocumentSnapshot
import vn.quanprolazer.fashione.domain.models.*

interface ReviewRepository {
    suspend fun addReview(review: Review, rating: Rating): Resource<Boolean>

    suspend fun getRatings(productId: String): Resource<List<Rating>>

    suspend fun getReviews(productId: String, lastVisibleId: String? = null): Resource<ReviewRetrofitResponse>

}