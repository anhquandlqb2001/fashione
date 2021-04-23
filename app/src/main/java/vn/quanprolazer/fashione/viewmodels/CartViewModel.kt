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
import vn.quanprolazer.fashione.data.domain.model.Resource
import vn.quanprolazer.fashione.data.domain.repository.ProductRepository
import vn.quanprolazer.fashione.utilities.mapInPlace
import vn.quanprolazer.fashione.utilities.notifyUpdate

@HiltViewModel
class CartViewModel @Inject constructor(private val orderRepository: OrderRepository,
                                        private val productRepository: ProductRepository
) : ViewModel() {

    private val _cartItems: MutableLiveData<Resource<MutableList<CartItem>>> by lazy {
        val liveData = MutableLiveData<Resource<MutableList<CartItem>>>(Resource.Loading(null))
        viewModelScope.launch {
            liveData.value = orderRepository.getCartItems()
        }
        return@lazy liveData
    }

    private val _flagFirstTime = MutableLiveData(true)

    val flagFirstTime: LiveData<Boolean> get() = _flagFirstTime

    fun offFlagFirstTime() {
        _flagFirstTime.value = null
    }

    val cartItems: LiveData<Resource<MutableList<CartItem>>> get() = _cartItems

    fun updateCartItemsImage() {
        (_cartItems.value as Resource.Success).data.mapInPlace {
            if (it.cartItemImg != null) return
            viewModelScope.launch {
                when (val cartItemImage =
                    productRepository.getProductImageByProductVariantId(it.variantId)) {
                    is Resource.Success -> it.cartItemImg = Resource.Success(cartItemImage.data)
                    is Resource.Error -> Resource.Error(cartItemImage.exception)
                    is Resource.Loading -> it.cartItemImg = Resource.Loading(null)
                }
            }
            it
        }
    }

    fun updateCartItemsProductName() {
        (_cartItems.value as Resource.Success).data.mapInPlace {
            if (it.product != null) return
            viewModelScope.launch {
                when (val product = productRepository.getProductByProductId(it.productId)) {
                    is Resource.Success -> it.product = Resource.Success(product.data)
                    is Resource.Error -> Resource.Error(product.exception)
                    is Resource.Loading -> it.product = Resource.Loading(null)
                }
            }
            it
        }
    }

    fun onQuantityControlClick(cartItem: CartItem, value: Int) {
        viewModelScope.launch {
            orderRepository.updateCartItem(cartItem.id, value)
        }
        cartItem.quantity = value
        refreshList()
    }

    fun removeCartItem(cartItemId: String) {
        viewModelScope.launch {
            orderRepository.removeCartItem(cartItemId)
        }
//        refreshList()
    }

    fun undoDeleteCartItem(cartItem: CartItem) {
        viewModelScope.launch {
            orderRepository.undoDeleteCartItem(cartItem)
        }
        refreshList()
    }

    private fun refreshList() = _cartItems.notifyUpdate()
}