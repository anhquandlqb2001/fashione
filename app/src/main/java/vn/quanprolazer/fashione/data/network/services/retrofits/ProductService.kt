/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.retrofits

import retrofit2.http.GET
import vn.quanprolazer.fashione.data.network.models.NetworkHighViewProductResponse

interface ProductService {
    @GET("products/most_view")
    suspend fun getHighViewProductIds(): NetworkHighViewProductResponse
}