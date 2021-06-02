/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import vn.quanprolazer.fashione.domain.models.*
import java.util.*

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
    @set:PropertyName("user_id")
    @get:PropertyName("user_id")
    @SerialName("user_id")
    var userId: String = "",
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
    var createdAt: String? = null
)

@ExperimentalSerializationApi
@Serializer(forClass = Date::class)
object DateSerializer : KSerializer<Date> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Date")

    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeString(value.time.toString())
    }

    override fun deserialize(decoder: Decoder): Date {
        return Date(decoder.decodeString().toLong())
    }
}


internal fun NetworkReviewFirestore.toDomainModel() = Review(
    productId = productId,
    orderItemId = orderItemId,
    userId = userId,
    reviewTitle = reviewTitle,
    reviewContent = reviewContent
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
    val username: String = "Ẩn danh",
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