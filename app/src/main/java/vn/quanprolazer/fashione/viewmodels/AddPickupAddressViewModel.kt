/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import android.content.Context
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import timber.log.Timber
import vn.quanprolazer.fashione.data.domain.model.BaseAddressPickup
import vn.quanprolazer.fashione.data.domain.model.BaseAddressPickupImpl
import vn.quanprolazer.fashione.data.domain.model.NewPickupAddress
import vn.quanprolazer.fashione.data.domain.model.ProvinceOrCity
import vn.quanprolazer.fashione.data.domain.repository.UserRepository
import vn.quanprolazer.fashione.utilities.LiveDataValidator
import vn.quanprolazer.fashione.utilities.LiveDataValidatorResolver
import java.io.InputStreamReader
import javax.inject.Inject

@HiltViewModel
class AddPickupAddressViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    val receiverName = MutableLiveData<String>()
    val receiverNameValidator =
        LiveDataValidator(receiverName).apply { //Whenever the condition of the predicate is true, the error message should be emitted
            addRule("Tên người nhận là bắt buộc") { it.isNullOrBlank() }
        }

    val phoneNumber = MutableLiveData<String>()
    val phoneNumberValidator = LiveDataValidator(phoneNumber).apply {
        addRule("Số điện thoại là bắt buộc") { it.isNullOrBlank() }
        addRule("Số điện thoại không hợp lệ") { it?.length!! <= 8 }
    }
    val provinceOrCity = MutableLiveData<String>()
    val districtOrTown = MutableLiveData<String>()
    val subdistrictOrVillage = MutableLiveData<String>()

    val address = MutableLiveData<String>()
    val addressValidator = LiveDataValidator(address).apply {
        addRule("Nhập số nhà / tên đường") { it.isNullOrBlank() }
    }

    private val addressType = MutableLiveData("Nhà riêng")

    //We will use a mediator so we can update the error state of our form fields
    //and the enabled state of our login button as the form data changes
    val isPickupAddressFormValidMediator = MediatorLiveData<Boolean>()

    init {
        isPickupAddressFormValidMediator.value = false
        isPickupAddressFormValidMediator.addSource(receiverName) { validateForm() }
        isPickupAddressFormValidMediator.addSource(phoneNumber) { validateForm() }
        isPickupAddressFormValidMediator.addSource(address) { validateForm() }
    }

    //This is called whenever the form fields changes
    private fun validateForm() {
        val validators = listOf(receiverNameValidator, phoneNumberValidator, addressValidator)
        val validatorResolver = LiveDataValidatorResolver(validators)
        isPickupAddressFormValidMediator.value = validatorResolver.isValid()
    }

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
            receiverName.value.toString(),
            phoneNumber.value.toString(),
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

    private fun readAsset(c: Context) : String = c.assets.open("dvhc.json").bufferedReader().use { it.readText() }


    //    BaseAddressPickup("-1", "--Chọn tỉnh / thành phố--")
    private var provincesOrCitiesData: List<ProvinceOrCity> = listOf()

    fun setProvincesOrCitiesData(c: Context) {
        val localeString: String = readAsset(c)
        provincesOrCitiesData = Json.decodeFromString(localeString)
    }

    private val _provinceOrCitySpinnerRows: MutableLiveData<List<BaseAddressPickupImpl>> by lazy {
        MutableLiveData(provincesOrCitiesData.map { BaseAddressPickupImpl(it.id, it.name) })
    }

    val provinceOrCitySpinnerRows: LiveData<List<BaseAddressPickupImpl>> get() = _provinceOrCitySpinnerRows
}