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
import vn.quanprolazer.fashione.data.domain.model.Resource
import vn.quanprolazer.fashione.data.domain.repository.FirebaseUserLiveData
import vn.quanprolazer.fashione.data.domain.repository.UserRepository
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
}