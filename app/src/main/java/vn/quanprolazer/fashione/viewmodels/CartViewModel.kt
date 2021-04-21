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
import vn.quanprolazer.fashione.data.domain.repository.ProductRepository
import vn.quanprolazer.fashione.utilities.mapInPlace

@HiltViewModel
class CartViewModel @Inject constructor(private val orderRepository: OrderRepository,
                                        private val productRepository: ProductRepository
) : ViewModel() {

    private val _cartItems: MutableLiveData<Result<MutableList<CartItem>>> by lazy {
        val liveData = MutableLiveData<Result<MutableList<CartItem>>>(Result.Loading(null))
        viewModelScope.launch {
            liveData.value = orderRepository.getCartItems()
        }
        return@lazy liveData
    }

    val cartItems: LiveData<Result<MutableList<CartItem>>> get() = _cartItems

    fun updateCartItemsImage() {
        (_cartItems.value as Result.Success).data.mapInPlace {
            viewModelScope.launch {
                when (val cartItemImage =
                    productRepository.getProductImageByProductVariantId(it.variantId)) {
                    is Result.Success -> it.cartItemImg = Result.Success(cartItemImage.data)
                    is Result.Error -> Result.Error(cartItemImage.exception)
                    is Result.Loading -> it.cartItemImg = Result.Loading(null)
                }
            }
            it
        }
    }
}