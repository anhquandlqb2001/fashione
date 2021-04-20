/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.data.domain.model.CartItem
import vn.quanprolazer.fashione.data.domain.repository.OrderRepository
import javax.inject.Inject
import vn.quanprolazer.fashione.data.domain.model.Result

@HiltViewModel
class CartViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _cartItems: MutableLiveData<Result<List<CartItem>>> by lazy {
        val liveData = MutableLiveData<Result<List<CartItem>>>()
        viewModelScope.launch {
            liveData.value = orderRepository.getCartItems()
        }
        return@lazy liveData
    }

    val cartItem: LiveData<Result<List<CartItem>>> get() = _cartItems


}