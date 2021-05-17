/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.models


data class PickupAddress(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val provinceOrCity: String,
    val districtOrTown: String,
    val subdistrictOrVillage: String,
    val address: String,
    val addressType: String,
    val default: Boolean
)