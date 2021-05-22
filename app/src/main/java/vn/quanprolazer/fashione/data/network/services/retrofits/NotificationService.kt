/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */
package vn.quanprolazer.fashione.data.network.services.retrofits

import retrofit2.http.GET
import retrofit2.http.Query
import vn.quanprolazer.fashione.data.network.models.NetworkNotificationOverview

interface NotificationService {

    @GET("notification/overview")
    suspend fun getNotificationOverview(
        @Query("token")
        idToken: String,
    ): List<NetworkNotificationOverview>
}