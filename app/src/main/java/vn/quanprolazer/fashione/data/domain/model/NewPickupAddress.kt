/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.model

data class NewPickupAddress(
    val userId: String,
    val name: String,
    val phoneNumber: String,
    val provinceOrCity: String,
    val districtOrTown: String,
    val subdistrictOrVillage: String,
    val address: String,
    val addressType: String
)