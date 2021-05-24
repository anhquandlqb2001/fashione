/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.retrofits

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import vn.quanprolazer.fashione.data.network.models.CreateOrderRequest
import vn.quanprolazer.fashione.data.network.models.NetworkDeliveryStatus
import vn.quanprolazer.fashione.data.network.models.UpdateOrderStatusRequest

interface OrderService {
    @GET("delivery/status")
    suspend fun getDeliveryStatus(
        @Query("token")
        idToken: String,
    ): List<NetworkDeliveryStatus>

    @POST("order")
    suspend fun createOrder(
        @Body
        data: CreateOrderRequest
    ): Boolean

    @POST("/order/status")
    suspend fun updateOrderStatus(
        @Body
        data: UpdateOrderStatusRequest
    ): Boolean
}