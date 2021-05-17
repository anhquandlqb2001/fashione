/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.models

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import vn.quanprolazer.fashione.domain.models.PickupAddress

data class NetworkPickupAddress(
    @DocumentId
    var id: String = "",
    @set:PropertyName("user_id")
    @get:PropertyName("user_id")
    var userId: String = "",
    var name: String = "",
    @set:PropertyName("phone_number")
    @get:PropertyName("phone_number")
    var phoneNumber: String = "",
    @set:PropertyName("province_or_city")
    @get:PropertyName("province_or_city")
    var provinceOrCity: String = "",
    @set:PropertyName("district_or_town")
    @get:PropertyName("district_or_town")
    var districtOrTown: String = "",
    @set:PropertyName("subdistrict_or_village")
    @get:PropertyName("subdistrict_or_village")
    var subdistrictOrVillage: String = "",
    var address: String = "",
    @set:PropertyName("address_type")
    @get:PropertyName("address_type")
    var addressType: String = "",
    var default: Boolean = false
)

internal fun NetworkPickupAddress.toDomainModel() = PickupAddress(
    id,
    name,
    phoneNumber,
    provinceOrCity,
    districtOrTown,
    subdistrictOrVillage,
    address,
    addressType,
    default
)