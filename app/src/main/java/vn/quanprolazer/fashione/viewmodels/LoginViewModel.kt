/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import vn.quanprolazer.fashione.data.domain.model.AuthenticationState
import vn.quanprolazer.fashione.data.domain.repository.UserRepository

class LoginViewModel : ViewModel() {

    private val userRepository: UserRepository by lazy {
        UserRepository()
    }
    val authenticationState: LiveData<AuthenticationState> by lazy {
        userRepository.getAuthenticateState()
    }
}

