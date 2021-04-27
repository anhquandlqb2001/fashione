/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber
import vn.quanprolazer.fashione.data.domain.mapper.OrderDataMapper
import vn.quanprolazer.fashione.data.domain.model.CheckoutItem
import vn.quanprolazer.fashione.data.domain.model.OrderData


class CheckoutViewModel @AssistedInject constructor(@Assisted private val checkoutItemsNor: List<CheckoutItem>) : ViewModel() {

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