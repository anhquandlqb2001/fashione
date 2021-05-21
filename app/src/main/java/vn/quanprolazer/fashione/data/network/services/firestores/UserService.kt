/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import vn.quanprolazer.fashione.data.network.models.NetworkPickupAddress
import vn.quanprolazer.fashione.domain.models.NewPickupAddress
import vn.quanprolazer.fashione.domain.models.Resource

interface UserService {

    suspend fun addPickupAddress(pickupAddress: NewPickupAddress): Resource<Boolean>

    suspend fun getPickupAddresses(userId: String): Resource<List<NetworkPickupAddress>>

    suspend fun getDefaultPickupAddress(userId: String): Resource<NetworkPickupAddress>

}