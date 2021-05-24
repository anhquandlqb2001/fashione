/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.retrofits

import retrofit2.http.GET
import retrofit2.http.Query
import vn.quanprolazer.fashione.data.network.models.NetworkReviewRetrofitResponse

interface ReviewService {
    @GET("reviews")
    suspend fun getReviews(
        @Query("product_id")
        productId: String,
        @Query("last_visible_id")
        lastVisibleId: String?
    ): NetworkReviewRetrofitResponse
}