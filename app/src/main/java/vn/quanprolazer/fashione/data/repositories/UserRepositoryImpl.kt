/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.repositories

import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vn.quanprolazer.fashione.data.network.models.toDomainModel
import vn.quanprolazer.fashione.data.network.services.firestores.UserService
import vn.quanprolazer.fashione.domain.models.AuthenticationState
import vn.quanprolazer.fashione.domain.models.NewPickupAddress
import vn.quanprolazer.fashione.domain.models.PickupAddress
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.repositories.FirebaseUserLiveData
import vn.quanprolazer.fashione.domain.repositories.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userService: UserService) :
    UserRepository {
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

        return when (data) {
            is Resource.Success -> {
                Resource.Success(data.data.map { it.toDomainModel() })
            }
            is Resource.Loading -> {
                Resource.Loading(null)
            }
            is Resource.Error -> Resource.Error(data.exception)
        }
    }

    override suspend fun getDefaultPickupAddress(): Resource<PickupAddress> {
        val user = getUser()
        val data = withContext(Dispatchers.IO) {
            userService.getDefaultPickupAddress(user.value!!.uid)
        }

        return when (data) {
            is Resource.Success -> {
                Resource.Success(data.data.toDomainModel())
            }
            is Resource.Loading -> {
                Resource.Loading(null)
            }
            is Resource.Error -> Resource.Error(data.exception)
        }
    }
}