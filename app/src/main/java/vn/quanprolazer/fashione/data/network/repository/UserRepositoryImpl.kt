/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.repository

import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vn.quanprolazer.fashione.data.domain.model.AuthenticationState
import vn.quanprolazer.fashione.data.domain.model.NewPickupAddress
import vn.quanprolazer.fashione.data.domain.model.PickupAddress
import vn.quanprolazer.fashione.data.domain.model.Resource
import vn.quanprolazer.fashione.data.domain.repository.FirebaseUserLiveData
import vn.quanprolazer.fashione.data.domain.repository.UserRepository
import vn.quanprolazer.fashione.data.network.dto.NetworkPickupAddress
import vn.quanprolazer.fashione.data.network.mapper.NetworkPickupAddressMapper
import vn.quanprolazer.fashione.data.network.service.UserService
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userService: UserService) : UserRepository  {
    override fun getAuthenticateState() = FirebaseUserLiveData.map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }

    override fun getUser() = FirebaseUserLiveData

    override suspend fun addPickupAddress(pickupAddress: NewPickupAddress): Resource<Boolean> {
        return withContext(Dispatchers.IO) {
            userService.addPickupAddress(pickupAddress)
        }
    }

    override suspend fun getPickupAddresses(): Resource<List<PickupAddress>> {
        val user = getUser()
        val data = withContext(Dispatchers.IO) {
            userService.getPickupAddresses(user.value!!.uid)
        }

        return when(data) {
            is Resource.Success -> { Resource.Success(NetworkPickupAddressMapper.map(data.data)) }
            is Resource.Loading -> { Resource.Loading(null) }
            is Resource.Error -> Resource.Error(data.exception)
        }
    }
}