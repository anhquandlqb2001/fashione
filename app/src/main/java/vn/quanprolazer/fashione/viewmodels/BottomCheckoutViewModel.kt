/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import vn.quanprolazer.fashione.data.domain.mapper.OrderDataMapper
import vn.quanprolazer.fashione.data.domain.model.CheckoutItem
import vn.quanprolazer.fashione.data.domain.model.OrderData
import vn.quanprolazer.fashione.utilities.notifyUpdate

class BottomCheckoutViewModel : ViewModel() {
    private val _visibleBottomCheckout: MutableLiveData<Boolean> by lazy {
        MutableLiveData(null)
    }

    val visibleBottomCheckout: LiveData<Boolean> get() = _visibleBottomCheckout

    fun updateVisibleBottomCheckout(value: Boolean = false) {
        _visibleBottomCheckout.value = value
    }

    private val _navigateToCheckoutScreen: MutableLiveData<List<CheckoutItem>> by lazy {
        MutableLiveData()
    }

    val navigateToCheckoutScreen: LiveData<List<CheckoutItem>> get() = _navigateToCheckoutScreen

    fun onNavigateToCheckoutScreen(orderData: OrderData) {
        _navigateToCheckoutScreen.value = OrderDataMapper.map(orderData)
        _navigateToCheckoutScreen.notifyUpdate()
    }

    fun onNavigateSuccess() {
        _navigateToCheckoutScreen.value = null
    }
}