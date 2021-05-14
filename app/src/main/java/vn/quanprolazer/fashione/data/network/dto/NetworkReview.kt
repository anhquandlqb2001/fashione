/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.dto

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import vn.quanprolazer.fashione.data.domain.model.Rating
import vn.quanprolazer.fashione.data.domain.model.Review


data class NetworkReview(
    @DocumentId
    var id: String = "",
    @set:PropertyName("product_id")
    @get:PropertyName("product_id")
    var productId: String = "",
    @set:PropertyName("rate_id")
    @get:PropertyName("rate_id")
    var rateId: String = "",
    @set:PropertyName("user_id")
    @get:PropertyName("user_id")
    var userId: String = "",
    val username: String = "",
    @set:PropertyName("review_title")
    @get:PropertyName("review_title")
    var reviewTitle: String = "",
    @set:PropertyName("review_content")
    @get:PropertyName("review_content")
    var reviewContent: String = "",
    @set:PropertyName("created_at")
    @get:PropertyName("created_at")
    var createdAt: String = ""
)

internal fun NetworkReview.toDomainModel() = Review(
    id, productId, rateId, userId, username, reviewTitle, reviewContent, createdAt
)

data class NetworkRating(
    @DocumentId
    var id: String = "",
    @set:PropertyName("review_id")
    @get:PropertyName("review_id")
    var reviewId: String = "",
    @set:PropertyName("product_id")
    @get:PropertyName("product_id")
    var productId: String = "",
    val rate: Int = -1
)

internal fun NetworkRating.toDomainModel() = Rating(
    id, reviewId, productId, rate
)

