/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import vn.quanprolazer.fashione.data.network.models.NetworkPickupAddress
import vn.quanprolazer.fashione.domain.models.NewPickupAddress

interface UserService {

    suspend fun addPickupAddress(pickupAddress: NewPickupAddress)

    suspend fun getPickupAddresses(userId: String): List<NetworkPickupAddress>

    suspend fun getDefaultPickupAddress(userId: String): NetworkPickupAddress

}