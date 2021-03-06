/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.domain.models.CartItem
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.repositories.CartRepository
import vn.quanprolazer.fashione.presentation.utilities.notifyUpdate
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _cartItems: MutableLiveData<Resource<MutableList<CartItem>>> by lazy {
        val liveData = MutableLiveData<Resource<MutableList<CartItem>>>(Resource.Loading)
        viewModelScope.launch {
            liveData.value = cartRepository.getCartItems()
        }
        return@lazy liveData
    }

    val cartItems: LiveData<Resource<MutableList<CartItem>>> get() = _cartItems

    /**
     * @param [value] fixed quantity
     */
    fun onQuantityControlClick(cartItem: CartItem, value: Int) {
        if (value <= 0) return
        viewModelScope.launch {
            cartRepository.updateCartItem(cartItem.id, value)
        }
        cartItem.quantity = value
        refreshList()
    }

    fun removeCartItem(cartItemId: String) {
        viewModelScope.launch {
            cartRepository.removeCartItem(cartItemId)
        }
    }

    fun undoDeleteCartItem(cartItem: CartItem) {
        viewModelScope.launch {
            cartRepository.undoDeleteCartItem(cartItem)
        }
        refreshList()
    }

    fun refreshList() = _cartItems.notifyUpdate()
}