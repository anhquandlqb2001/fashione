/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PickupAddressViewModel : ViewModel() {

    private val _navigateToAddPickupAddress: MutableLiveData<Boolean> by lazy {
        MutableLiveData()
    }

    val navigateToAddPickupAddress: LiveData<Boolean> get() = _navigateToAddPickupAddress

    fun onNavigateToAddPickupAddress() {
        _navigateToAddPickupAddress.value = true
    }

    fun doneNavigateToAddPickupAddress() {
        _navigateToAddPickupAddress.value = null
    }
}