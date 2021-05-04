/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.data.domain.model.PickupAddress
import vn.quanprolazer.fashione.data.domain.model.ProductImage
import vn.quanprolazer.fashione.data.domain.model.Resource
import vn.quanprolazer.fashione.data.domain.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class PickupAddressViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

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
        val liveData = MutableLiveData<Resource<List<PickupAddress>>>()
        viewModelScope.launch {
            liveData.value = userRepository.getPickupAddresses()
        }
        return@lazy liveData
    }

    val pickupAddresses: LiveData<Resource<List<PickupAddress>>> get() = _pickupAddresses


}