/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.model

import kotlinx.serialization.Serializable

interface BaseAddressPickup {
    val id: String
    val name: String
}

data class BaseAddressPickupImpl(override val id: String, override val name: String
) : BaseAddressPickup

// tinh / tp
@Serializable
data class ProvinceOrCity(override val id: String,
                          override val name: String,
                          val districts: List<DistrictOrTown>
) : BaseAddressPickup

// quan huyen
@Serializable
data class DistrictOrTown(override val id: String,
                          override val name: String,
                          val wards: List<SubDistrictOrVillage>
) : BaseAddressPickup

// phuong xa
@Serializable
data class SubDistrictOrVillage(override val id: String,
                                override val name: String,
                                val level: String
) : BaseAddressPickup