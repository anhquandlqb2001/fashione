/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewPickupAddress(
    @SerialName("user_id")
    var userId: String,
    var name: String,
    @SerialName("phone_number")
    var phoneNumber: String,
    @SerialName("province_or_city")
    var provinceOrCity: String,
    @SerialName("district_or_town")
    var districtOrTown: String,
    @SerialName("subdistrict_or_village")
    var subdistrictOrVillage: String,
    var address: String,
    @SerialName("address_type")
    var addressType: String
)