/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.service

import vn.quanprolazer.fashione.data.domain.model.NewPickupAddress
import vn.quanprolazer.fashione.data.domain.model.Resource

interface UserService {

    suspend fun addPickupAddress(pickupAddress: NewPickupAddress) : Resource<Boolean>

}