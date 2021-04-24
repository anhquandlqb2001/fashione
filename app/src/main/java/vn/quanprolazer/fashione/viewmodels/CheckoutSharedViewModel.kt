/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vn.quanprolazer.fashione.data.domain.model.CartItem
import vn.quanprolazer.fashione.data.domain.model.OrderData

class CheckoutSharedViewModel : ViewModel() {
    private val _orderData: MutableLiveData<OrderData> by lazy {
        MutableLiveData(OrderData())
    }
    val orderData: LiveData<OrderData> get() = _orderData

    fun updateOrderData(cartItems: List<CartItem>?) {
        _orderData.value = OrderData()
        cartItems?.let {
            var flagOrderEmpty = true
            cartItems.map { cartItem ->
                if (cartItem.isChecked) {
                    val newPrice = _orderData.value?.totalPrice?.toFloat()
                        ?.plus((cartItem.price.toFloat() * cartItem.quantity))
                    _orderData.value?.totalPrice = newPrice.toString()
                    _orderData.value?.items?.add(cartItem)
                    flagOrderEmpty = false
                }
            }
            if (flagOrderEmpty) {
                _orderData.value?.totalPrice = "0"
                _orderData.value?.items = mutableListOf()
            }
        }
        notifyUpdate()
    }

    private fun notifyUpdate() {
        _orderData.value = _orderData.value
    }

    private val _visibleBottomCheckout: MutableLiveData<Boolean> by lazy {
        MutableLiveData(null)
    }

    val visibleBottomCheckout: LiveData<Boolean> get() = _visibleBottomCheckout

    fun updateVisibleBottomCheckout(value: Boolean = false) {
        _visibleBottomCheckout.value = value
    }

}