/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.quanprolazer.fashione.data.domain.repository.FirebaseUserLiveData
import vn.quanprolazer.fashione.data.domain.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class PersonalViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    val user: FirebaseUserLiveData by lazy {
        userRepository.getUser()
    }

    private val _navigateToPurchaseMenu: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val navigateToPurchaseMenu: LiveData<Boolean> get() = _navigateToPurchaseMenu

    fun onNavigateToPurchaseMenu() {
        _navigateToPurchaseMenu.value = true
    }

    fun doneNavigateToPurchaseMenu() {
        _navigateToPurchaseMenu.value = null
    }
}