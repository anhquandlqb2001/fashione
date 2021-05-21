/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.retrofits

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import vn.quanprolazer.fashione.domain.models.DeviceToken

interface UserService {
    @Headers("Content-Type: application/json")
    @POST("notification/token")
    suspend fun saveDeviceToken(
        @Body deviceToken: DeviceToken
    ): Boolean
}