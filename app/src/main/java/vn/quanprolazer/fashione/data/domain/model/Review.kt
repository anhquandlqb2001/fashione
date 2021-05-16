/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.parcelize.Parcelize
import vn.quanprolazer.fashione.data.network.dto.NetworkRating
import vn.quanprolazer.fashione.data.network.dto.NetworkReview

@Parcelize
data class PurchaseToAddReview(
    val product: Product,
    val productVariantOption: ProductVariantOption,
    val productImage: ProductImage,
    val orderItemId: String,
    val variantName: String
) : Parcelable

data class Review(
    val id: String? = null,
    val productId: String,
    val orderItemId: String,
    val rateId: String? = null,
    val userId: String? = null,
    val username: String? = null,
    val reviewTitle: String,
    val reviewContent: String,
    val createdAt: String
)

internal fun Review.toNetworkModel() = NetworkReview(
    productId = productId,
    reviewContent = reviewContent,
    reviewTitle = reviewTitle,
    createdAt = createdAt,
    orderItemId = orderItemId
)

data class Rating(
    val id: String? = null,
    val reviewId: String? = null,
    val productId: String,
    val rate: Int
)

internal fun Rating.toNetworkModel() = NetworkRating(
    productId = productId,
    rate = rate
)

data class ReviewWithRating(
    val review: Review,
    val rating: Rating
)

data class OverviewRating(
    val averageRate: Float,
    val countRate: Int
)

data class ReviewWithRatingResponse(
    val reviews: Resource<List<ReviewWithRating>>,
    val lastVisible: DocumentSnapshot?
)