/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import androidx.lifecycle.ViewModel
import vn.quanprolazer.fashione.domain.repository.UserRepository

class LoginViewModel : ViewModel() {

    private val userRepository = UserRepository()

    val authenticationState = userRepository.getAuthenticateState()

}

