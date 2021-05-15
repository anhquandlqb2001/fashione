/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.model

import vn.quanprolazer.fashione.data.network.dto.NetworkRating
import vn.quanprolazer.fashione.data.network.dto.NetworkReview

data class Review(
    val id: String? = null,
    val productId: String,
    val rateId: String,
    val userId: String,
    val username: String,
    val reviewTitle: String,
    val reviewContent: String,
    val createdAt: String
)

internal fun Review.toNetworkModel() = NetworkReview(
    productId = productId,
    rateId = rateId,
    username = username,
    reviewContent = reviewContent,
    reviewTitle = reviewTitle,
    createdAt = createdAt,
    userId = userId
)

data class Rating(
    val id: String? = null,
    val reviewId: String,
    val productId: String,
    val rate: Int
)

internal fun Rating.toNetworkModel() = NetworkRating(
    reviewId = reviewId,
    productId = productId,
    rate = rate
)

data class ReviewWithRating(
    val review: Review,
    val rating: Rating
)

