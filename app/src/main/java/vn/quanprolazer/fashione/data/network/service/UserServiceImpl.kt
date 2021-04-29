/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.service

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import vn.quanprolazer.fashione.data.domain.model.NewPickupAddress
import vn.quanprolazer.fashione.data.domain.model.Resource
import vn.quanprolazer.fashione.data.network.mapper.toHashMap

class UserServiceImpl : UserService {
    override suspend fun addPickupAddress(pickupAddress: NewPickupAddress): Resource<Boolean> {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("addresses").add(pickupAddress.toHashMap()).await()
            Resource.Success(true)
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }
}