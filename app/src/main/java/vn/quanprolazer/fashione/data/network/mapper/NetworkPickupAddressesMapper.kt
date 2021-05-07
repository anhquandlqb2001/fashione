/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.mapper

import vn.quanprolazer.fashione.data.domain.model.PickupAddress
import vn.quanprolazer.fashione.data.mapper.ListMapper
import vn.quanprolazer.fashione.data.mapper.Mapper
import vn.quanprolazer.fashione.data.network.dto.NetworkPickupAddress


object NetworkPickupAddressesMapper : ListMapper<NetworkPickupAddress, PickupAddress> {
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
                it.addressType,
                it.default
            )
        }
    }
}

object NetworkPickupAddressMapper : Mapper<NetworkPickupAddress, PickupAddress> {
    override fun map(input: NetworkPickupAddress) = PickupAddress(
        input.id,
        input.name,
        input.phoneNumber,
        input.provinceOrCity,
        input.districtOrTown,
        input.subdistrictOrVillage,
        input.address,
        input.addressType,
        input.default
    )

}