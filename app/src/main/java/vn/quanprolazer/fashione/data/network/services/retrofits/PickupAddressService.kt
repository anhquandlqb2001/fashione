/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.retrofits

import retrofit2.http.GET
import retrofit2.http.Path
import vn.quanprolazer.fashione.domain.models.DistrictOrTown
import vn.quanprolazer.fashione.domain.models.ProvinceOrCity
import vn.quanprolazer.fashione.domain.models.SubdistrictOrVillage

interface PickupAddressService {
    @GET("tinh-tp-converted")
    suspend fun getProvincesOrCities(): List<ProvinceOrCity>

    @GET("quan-huyen-converted/{parent_code}.json")
    suspend fun getDistrictsOrTowns(
        @Path("parent_code")
        parentCode: String
    ): List<DistrictOrTown>

    @GET("xa-phuong-converted/{parent_code}.json")
    suspend fun getSubDistrictsOrVillages(
        @Path("parent_code")
        parentCode: String
    ): List<SubdistrictOrVillage>
}