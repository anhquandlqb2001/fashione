/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.quanprolazer.fashione.data.domain.model.AuthenticationState
import vn.quanprolazer.fashione.data.domain.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    val authenticationState: LiveData<AuthenticationState> by lazy {
        userRepository.getAuthenticateState()
    }
}

