/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.repositories

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import vn.quanprolazer.fashione.domain.models.AuthenticationState
import vn.quanprolazer.fashione.domain.models.NewPickupAddress
import vn.quanprolazer.fashione.domain.models.PickupAddress
import vn.quanprolazer.fashione.domain.models.Resource

interface UserRepository {
    fun getAuthenticateState(): LiveData<AuthenticationState>

    fun getUser(): FirebaseUserLiveData

    suspend fun getToken(): String?

    suspend fun addPickupAddress(pickupAddress: NewPickupAddress): Resource<PickupAddress>

    suspend fun getPickupAddresses(): Resource<List<PickupAddress>>

    suspend fun getDefaultPickupAddress(): Resource<PickupAddress>

    suspend fun saveDeviceToken(token: String): Resource<Boolean>
}

object FirebaseUserLiveData : LiveData<FirebaseUser?>() {
    private val firebaseAuth = FirebaseAuth.getInstance()

    // TODO set the value of this FireUserLiveData object by hooking it up to equal the value of the
    //  current FirebaseUser. You can utilize the FirebaseAuth.AuthStateListener callback to get
    //  updates on the current Firebase user logged into the app.
    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        // Use the FirebaseAuth instance instantiated at the beginning of the class to get an entry
        // point into the Firebase Authentication SDK the app is using.
        // With an instance of the FirebaseAuth class, you can now query for the current user.
        value = firebaseAuth.currentUser
    }

    // When this object has an active observer, start observing the FirebaseAuth state to see if
    // there is currently a logged in user.
    override fun onActive() {
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    // When this object no longer has an active observer, stop observing the FirebaseAuth state to
    // prevent memory leaks.
    override fun onInactive() {
        firebaseAuth.removeAuthStateListener(authStateListener)
    }
}