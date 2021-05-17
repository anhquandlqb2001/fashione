/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.quanprolazer.fashione.domain.models.AuthenticationState
import vn.quanprolazer.fashione.domain.repositories.UserRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    val authenticationState: LiveData<AuthenticationState> by lazy {
        userRepository.getAuthenticateState()
    }
}

