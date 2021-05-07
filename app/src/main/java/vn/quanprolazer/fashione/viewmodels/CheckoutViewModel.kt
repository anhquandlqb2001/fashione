/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import androidx.lifecycle.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.data.domain.model.CheckoutItem
import vn.quanprolazer.fashione.data.domain.model.PickupAddress
import vn.quanprolazer.fashione.data.domain.model.Resource
import vn.quanprolazer.fashione.data.domain.repository.UserRepository


class CheckoutViewModel @AssistedInject constructor(private val userRepository: UserRepository, @Assisted private val checkoutItemsNor: List<CheckoutItem>) : ViewModel() {

    private val _checkoutItems: MutableLiveData<List<CheckoutItem>> by lazy {
        MutableLiveData(checkoutItemsNor)
    }

    val checkoutItems: LiveData<List<CheckoutItem>> get() = _checkoutItems

    private val _navigateToPickupAddress: MutableLiveData<Boolean> by lazy {
        MutableLiveData()
    }

    val navigateToPickupAddress: LiveData<Boolean> get() = _navigateToPickupAddress

    fun onNavigateToPickupAddress() {
        _navigateToPickupAddress.value = true
    }

    fun doneNavigate() {
        _navigateToPickupAddress.value = null
    }

    private val _defaultCheckoutAddress: MutableLiveData<Resource<PickupAddress>> by lazy {
        val liveData = MutableLiveData<Resource<PickupAddress>>()
        viewModelScope.launch {
            liveData.value = userRepository.getDefaultPickupAddress()
        }
        return@lazy liveData
    }
    val defaultCheckoutAddress: LiveData<Resource<PickupAddress>> get() = _defaultCheckoutAddress

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(checkoutItemsNor: List<CheckoutItem>): CheckoutViewModel
    }

    companion object {
        fun provideFactory(assistedFactory: AssistedFactory, checkoutItemsNor: List<CheckoutItem>
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(checkoutItemsNor) as T
            }
        }
    }
}