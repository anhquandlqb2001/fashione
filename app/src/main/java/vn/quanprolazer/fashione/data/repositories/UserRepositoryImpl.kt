/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.repositories

import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber
import vn.quanprolazer.fashione.data.network.models.toDomainModel
import vn.quanprolazer.fashione.data.network.services.firestores.UserService
import vn.quanprolazer.fashione.domain.models.*
import vn.quanprolazer.fashione.domain.repositories.FirebaseUserLiveData
import vn.quanprolazer.fashione.domain.repositories.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val userRetrofitService: vn.quanprolazer.fashione.data.network.services.retrofits.UserService
) :
    UserRepository {
    override fun getAuthenticateState() = FirebaseUserLiveData.map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }

    override fun getUser() = FirebaseUserLiveData

    override suspend fun addPickupAddress(pickupAddress: NewPickupAddress) = try {
        withContext(Dispatchers.IO) {
            userService.addPickupAddress(pickupAddress)
        }
        Resource.Success(true)
    } catch (e: Exception) {
        Timber.e(e)
        Resource.Error(e)
    }

    override suspend fun getPickupAddresses(): Resource<List<PickupAddress>> {
        val user = getUser().value ?: return Resource.Error(Exception("NOT_LOGIN"))
        return try {
            val response = withContext(Dispatchers.IO) {
                userService.getPickupAddresses(user.uid)
            }
            Resource.Success(response.map { it.toDomainModel() })
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }

    override suspend fun getDefaultPickupAddress(): Resource<PickupAddress> {
        val user = getUser().value ?: return Resource.Error(Exception("NOT_LOGIN"))
        return try {
            val response = withContext(Dispatchers.IO) {
                userService.getDefaultPickupAddress(user.uid)
            }
            Resource.Success(response.toDomainModel())
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }

    override suspend fun saveDeviceToken(token: String): Resource<Boolean> {
        val user = getUser().value ?: return Resource.Error(Exception())
        try {
            withContext(Dispatchers.IO) {
                userRetrofitService.saveDeviceToken(DeviceToken(user.uid, token))
            }
        } catch (e: Exception) {
            Timber.e(e)
            return Resource.Error(Exception("Error save token"))
        }
        return Resource.Success(true)
    }

    override suspend fun getToken(): String? {
        val user = getUser().value ?: return null
        return try {
            user.getIdToken(true)
                .await()
                .token
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }
}