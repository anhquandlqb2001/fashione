/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import vn.quanprolazer.fashione.domain.models.PickupAddress
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.repositories.UserRepository
import javax.inject.Inject

@HiltViewModel
class PickupAddressViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

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

    private val _pickupAddresses: MutableLiveData<Resource<List<PickupAddress>>> by lazy {
        MutableLiveData()
    }

    fun fetchPickupAddresses() {
        _pickupAddresses.value = Resource.Loading
        viewModelScope.launch {
            _pickupAddresses.value = userRepository.getPickupAddresses()
        }
    }

    val pickupAddresses: LiveData<Resource<List<PickupAddress>>> get() = _pickupAddresses

    private val _navigateBackToCheckout: MutableLiveData<PickupAddress> by lazy { MutableLiveData() }
    val navigateBackToCheckout: LiveData<PickupAddress> get() = _navigateBackToCheckout

    fun onNavigateBackToCheckout(address: PickupAddress) {
        _navigateBackToCheckout.value = address
    }

    fun doneNavigateBackToCheckout() {
        _navigateBackToCheckout.value = null
    }

    fun resetLiveData() {
        _pickupAddresses.value = null
    }

}