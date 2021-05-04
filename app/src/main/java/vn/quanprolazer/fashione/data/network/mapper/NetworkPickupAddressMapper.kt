/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.mapper

import vn.quanprolazer.fashione.data.domain.model.Category
import vn.quanprolazer.fashione.data.domain.model.PickupAddress
import vn.quanprolazer.fashione.data.mapper.ListMapper
import vn.quanprolazer.fashione.data.network.dto.NetworkCategory
import vn.quanprolazer.fashione.data.network.dto.NetworkPickupAddress


object NetworkPickupAddressMapper : ListMapper<NetworkPickupAddress, PickupAddress> {
    override fun map(input: List<NetworkPickupAddress>): List<PickupAddress> {
        return input.map {
            PickupAddress(
                it.id,
                it.name,
                it.phoneNumber,
                it.provinceOrCity,
                it.districtOrTown,
                it.subdistrictOrVillage,
                it.address,
                it.addressType
            )
        }
    }
}