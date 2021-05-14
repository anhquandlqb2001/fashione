/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.model

data class Review(
    val id: String,
    val productId: String,
    val rateId: String,
    val userId: String,
    val username: String,
    val reviewTitle: String,
    val reviewContent: String,
    val createdAt: String
)

data class Rating(
    val id: String ,
    val reviewId: String,
    val productId: String,
    val rate: Int
)

