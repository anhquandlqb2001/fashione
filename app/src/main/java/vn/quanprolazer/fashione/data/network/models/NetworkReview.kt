/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.models

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import vn.quanprolazer.fashione.domain.models.*

@Serializable
data class NetworkReviewFirestore(
    @DocumentId
    @Exclude
    var id: String = "",
    @set:PropertyName("product_id")
    @get:PropertyName("product_id")
    @SerialName("product_id")
    var productId: String = "",
    @set:PropertyName("order_item_id")
    @get:PropertyName("order_item_id")
    @SerialName("order_item_id")
    var orderItemId: String = "",
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

internal fun NetworkReviewFirestore.toDomainModel() = Review(
    id, productId, orderItemId, rateId, userId, username, reviewTitle, reviewContent, createdAt
)

@Serializable
data class NetworkRating(
    @DocumentId
    @Exclude
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


@Serializable
data class NetworkReviewRetrofitResponse(
    val data: List<NetworkReviewRetrofit>,
    @SerialName("last_visible_id")
    val lastVisibleId: String?
)

internal fun NetworkReviewRetrofitResponse.toDomainModel() =
    ReviewRetrofitResponse(data.map { it.toDomainModel() }, lastVisibleId)


@Serializable
data class NetworkReviewRetrofit(
    val id: String,
    val username: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("order_item_id")
    val orderItemId: String,
    @SerialName("photo_url")
    val photoUrl: String,
    @SerialName("product_id")
    val productId: String,
    @SerialName("product_name")
    val productName: String,
    val quantity: Int,
    val rate: Int,
    @SerialName("review_content")
    val reviewContent: String,
    @SerialName("review_title")
    val reviewTitle: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("variant_id")
    val variantId: String,
    @SerialName("variant_name")
    val variantName: String,
    @SerialName("variant_option_id")
    val variantOptionId: String,
    @SerialName("variant_value")
    val variantValue: String
)

internal fun NetworkReviewRetrofit.toDomainModel() = ReviewRetrofit(
    id,
    username,
    createdAt,
    orderItemId,
    photoUrl,
    productId,
    productName,
    quantity,
    rate,
    reviewContent,
    reviewTitle,
    userId,
    variantId,
    variantName,
    variantOptionId,
    variantValue
)

enum class NetworkReviewStatus {
    YES,
    NO
}

internal fun NetworkReviewStatus.toDomainModel() = ReviewStatus.valueOf(this.name)