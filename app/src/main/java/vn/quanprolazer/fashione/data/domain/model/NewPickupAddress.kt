/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.model


data class NewPickupAddress(
    var userId: String,
    var name: String,
    var phoneNumber: String,
    var provinceOrCity: String,
    var districtOrTown: String,
    var subdistrictOrVillage: String,
    var address: String,
    var addressType: String
)