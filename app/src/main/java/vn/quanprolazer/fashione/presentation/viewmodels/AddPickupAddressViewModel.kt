/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.viewmodels

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.domain.models.*
import vn.quanprolazer.fashione.domain.repositories.NetworkRepository
import vn.quanprolazer.fashione.domain.repositories.UserRepository
import vn.quanprolazer.fashione.presentation.utilities.LiveDataValidator
import vn.quanprolazer.fashione.presentation.utilities.LiveDataValidatorResolver
import javax.inject.Inject

@HiltViewModel
class AddPickupAddressViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val networkRepository: NetworkRepository
) : ViewModel() {

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
    private val _provinceOrCity = MutableLiveData<String>()
    fun updateProvinceOrCityFormField(string: String) {
        _provinceOrCity.value = string
    }

    private val _districtOrTown = MutableLiveData<String>()
    fun updateDistrictOrTownFormField(string: String) {
        _districtOrTown.value = string
    }

    private val _subdistrictOrVillage = MutableLiveData<String>()
    fun updateSubdistrictOrVillageFormField(string: String) {
        _subdistrictOrVillage.value = string
    }

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

    private val _onSavePickupAddress: MutableLiveData<Resource<Boolean>> by lazy {
        MutableLiveData()
    }
    val onSavePickupAddress: LiveData<Resource<Boolean>> get() = _onSavePickupAddress

    fun onClickSave() {
        _onSavePickupAddress.value = Resource.Loading(null)

        _addPickupAddress.value = NewPickupAddress(
            userRepository.getUser().value?.uid.toString(),
            receiverName.value.toString(),
            phoneNumber.value.toString(),
            _districtOrTown.value.toString(),
            _provinceOrCity.value.toString(),
            _subdistrictOrVillage.value.toString(),
            address.value.toString(),
            addressType.value.toString()
        )

        viewModelScope.launch {
            _addPickupAddress.value?.let {
                _onSavePickupAddress.value = userRepository.addPickupAddress(it)
            }
        }
    }

    private val _provinceOrCityData: MutableLiveData<List<ProvinceOrCity>> by lazy {
        val liveData = MutableLiveData<List<ProvinceOrCity>>()
        viewModelScope.launch {
            liveData.value = networkRepository.getProvincesOrCities()
        }
        return@lazy liveData
    }

    val provinceOrCityRows: LiveData<List<BaseAddressPickupImpl>>
        get() = Transformations.map(_provinceOrCityData) {
            it.map { provinceOrCity ->
                BaseAddressPickupImpl(
                    provinceOrCity.code,
                    provinceOrCity.name,
                    provinceOrCity.nameWithType
                )
            }
        }


    private val _districtOrTownData: MutableLiveData<List<DistrictOrTown>> by lazy {
        MutableLiveData(listOf())
    }
    val districtOrTownRows: LiveData<List<BaseAddressPickupImpl>>
        get() = Transformations.map(_districtOrTownData) {
            it.map { districtOrTown ->
                BaseAddressPickupImpl(
                    districtOrTown.code,
                    districtOrTown.name,
                    districtOrTown.nameWithType
                )
            }
        }

    fun updateDistrictOrTown(parentCode: String) {
        viewModelScope.launch {
            _districtOrTownData.value = networkRepository.getDistrictsOrTowns(parentCode)
        }
    }

    private val _subdistrictOrVillageData: MutableLiveData<List<SubdistrictOrVillage>> by lazy {
        MutableLiveData(listOf())
    }
    val subdistrictOrVillageRows: LiveData<List<BaseAddressPickupImpl>>
        get() = Transformations.map(_subdistrictOrVillageData) {
            it.map { subdistrictOrVillage ->
                BaseAddressPickupImpl(
                    subdistrictOrVillage.code,
                    subdistrictOrVillage.name,
                    subdistrictOrVillage.nameWithType
                )
            }
        }

    fun updateSubdistrictOrVillage(parentCode: String) {
        viewModelScope.launch {
            _subdistrictOrVillageData.value =
                networkRepository.getSubdistrictsOrVillages(parentCode)
        }
    }

}