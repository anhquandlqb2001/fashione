/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import vn.quanprolazer.fashione.data.network.models.NetworkPickupAddress
import vn.quanprolazer.fashione.domain.models.NewPickupAddress
import vn.quanprolazer.fashione.helpers.toHashMap

class UserServiceImpl : UserService {
    override suspend fun addPickupAddress(pickupAddress: NewPickupAddress): String {
        return FirebaseFirestore.getInstance().collection("addresses")
            .add(pickupAddress.toHashMap())
            .await().id
    }


    override suspend fun getPickupAddresses(userId: String) =
        FirebaseFirestore.getInstance().collection("addresses").get().await().documents.mapNotNull {
            it.toObject(NetworkPickupAddress::class.java)
        }

    override suspend fun getDefaultPickupAddress(userId: String) =
        FirebaseFirestore.getInstance().collection("addresses").whereEqualTo("default", true).get()
            .await()
            .documents
            .mapNotNull { it.toObject(NetworkPickupAddress::class.java) }[0]
}
