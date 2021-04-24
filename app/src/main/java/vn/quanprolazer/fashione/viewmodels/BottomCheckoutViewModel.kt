/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BottomCheckoutViewModel : ViewModel() {
    private val _visibleBottomCheckout: MutableLiveData<Boolean> by lazy {
        MutableLiveData(null)
    }

    val visibleBottomCheckout: LiveData<Boolean> get() = _visibleBottomCheckout

    fun updateVisibleBottomCheckout(value: Boolean = false) {
        _visibleBottomCheckout.value = value
    }
}