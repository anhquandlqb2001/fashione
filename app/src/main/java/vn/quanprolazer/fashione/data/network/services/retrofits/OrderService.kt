/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.retrofits

import retrofit2.http.GET
import retrofit2.http.Query
import vn.quanprolazer.fashione.data.network.models.NetworkDeliveryStatus

interface OrderService {
    @GET("delivery/status")
    suspend fun getDeliveryStatus(
        @Query("token")
        idToken: String,
    ): List<NetworkDeliveryStatus>
}