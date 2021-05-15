/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.dto

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import vn.quanprolazer.fashione.data.domain.model.Rating
import vn.quanprolazer.fashione.data.domain.model.Review

@Serializable
data class NetworkReview(
    @DocumentId
    @Exclude
    var id: String = "",
    @set:PropertyName("product_id")
    @get:PropertyName("product_id")
    @SerialName("product_id")
    var productId: String = "",
    @set:PropertyName("rate_id")
    @get:PropertyName("rate_id")
    @SerialName("rate_id")
    var rateId: String = "",
    @set:PropertyName("user_id")
    @get:PropertyName("user_id")
    @SerialName("user_id")
    var userId: String = "",
    val username: String = "",
    @set:PropertyName("review_title")
    @get:PropertyName("review_title")
    @SerialName("review_title")
    var reviewTitle: String = "",
    @set:PropertyName("review_content")
    @get:PropertyName("review_content")
    @SerialName("review_content")
    var reviewContent: String = "",
    @set:PropertyName("created_at")
    @get:PropertyName("created_at")
    @SerialName("created_at")
    var createdAt: String = ""
)

internal fun NetworkReview.toDomainModel() = Review(
    id, productId, rateId, userId, username, reviewTitle, reviewContent, createdAt
)

@Serializable
data class NetworkRating(
    @DocumentId
    var id: String = "",
    @set:PropertyName("review_id")
    @get:PropertyName("review_id")
    @SerialName("review_id")
    var reviewId: String = "",
    @set:PropertyName("product_id")
    @get:PropertyName("product_id")
    @SerialName("product_id")
    var productId: String = "",
    val rate: Int = -1
)

internal fun NetworkRating.toDomainModel() = Rating(
    id, reviewId, productId, rate
)

