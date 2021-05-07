/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.service

import vn.quanprolazer.fashione.data.domain.model.NewPickupAddress
import vn.quanprolazer.fashione.data.domain.model.Resource
import vn.quanprolazer.fashione.data.network.dto.NetworkPickupAddress

interface UserService {

    suspend fun addPickupAddress(pickupAddress: NewPickupAddress) : Resource<Boolean>

    suspend fun getPickupAddresses(userId: String): Resource<List<NetworkPickupAddress>>

    suspend fun getDefaultPickupAddress(userId: String): Resource<NetworkPickupAddress>
}