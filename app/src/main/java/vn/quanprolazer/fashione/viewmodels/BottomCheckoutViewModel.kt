/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vn.quanprolazer.fashione.data.domain.model.OrderData

class BottomCheckoutViewModel : ViewModel() {
    private val _visibleBottomCheckout: MutableLiveData<Boolean> by lazy {
        MutableLiveData(null)
    }

    val visibleBottomCheckout: LiveData<Boolean> get() = _visibleBottomCheckout

    fun updateVisibleBottomCheckout(value: Boolean = false) {
        _visibleBottomCheckout.value = value
    }

    private val _navigateToCheckoutScreen: MutableLiveData<Boolean> by lazy {
        MutableLiveData()
    }

    val navigateToCheckoutScreen: LiveData<Boolean> get() = _navigateToCheckoutScreen

    fun onNavigateToCheckoutScreen() {
        _navigateToCheckoutScreen.value = true
    }

    fun onNavigateSuccess() {
        _navigateToCheckoutScreen.value = null
    }

}