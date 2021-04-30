/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import vn.quanprolazer.fashione.data.domain.model.NewPickupAddress
import vn.quanprolazer.fashione.data.domain.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class AddPickupAddressViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    val name = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val provinceOrCity = MutableLiveData<String>()
    val districtOrTown = MutableLiveData<String>()
    val subdistrictOrVillage = MutableLiveData<String>()
    val address = MutableLiveData<String>()
    private val addressType = MutableLiveData("Nha rieng")

    fun updateAddressTypeToPersonal() {
        addressType.value = "Nhà riêng"
    }


    fun updateAddressTypeToOrganization() {
        addressType.value = "Cơ quan"
    }

    private val _addPickupAddress: MutableLiveData<NewPickupAddress> by lazy {
        MutableLiveData()
    }

    val addPickupAddress: LiveData<NewPickupAddress> get() = _addPickupAddress

    fun onClickSave() {
        _addPickupAddress.value = NewPickupAddress(
            userRepository.getUser().value?.uid.toString(),
            name.value.toString(),
            phone.value.toString(),
            districtOrTown.value.toString(),
            provinceOrCity.value.toString(),
            subdistrictOrVillage.value.toString(),
            address.value.toString(),
            addressType.value.toString()
        )
        viewModelScope.launch {
            _addPickupAddress.value?.let { userRepository.addPickupAddress(it) }
        }
    }
}