/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.models

import com.google.firebase.firestore.Exclude
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewPickupAddress(
    @Exclude
    val id: String = "",
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

internal fun NewPickupAddress.toDomainModel() = PickupAddress(
    id = id,
    name = name,
    phoneNumber = phoneNumber,
    provinceOrCity = provinceOrCity,
    districtOrTown = districtOrTown,
    subdistrictOrVillage = subdistrictOrVillage,
    address = address,
    addressType = addressType,
    default = false
)