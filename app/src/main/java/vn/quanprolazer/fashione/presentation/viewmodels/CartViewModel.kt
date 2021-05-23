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
import vn.quanprolazer.fashione.domain.repositories.ProductRepository
import vn.quanprolazer.fashione.presentation.utilities.mapInPlace
import vn.quanprolazer.fashione.presentation.utilities.notifyUpdate
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _cartItems: MutableLiveData<Resource<MutableList<CartItem>>> by lazy {
        val liveData = MutableLiveData<Resource<MutableList<CartItem>>>(Resource.Loading(null))
        viewModelScope.launch {
            liveData.value = cartRepository.getCartItems()
        }
        return@lazy liveData
    }

    val cartItems: LiveData<Resource<MutableList<CartItem>>> get() = _cartItems

    private var _flagFirstTimeLoad: Boolean = true

    val flagFirstTimeLoad: Boolean get() = _flagFirstTimeLoad

    fun doneFirstTimeLoad() {
        _flagFirstTimeLoad = false
    }

    fun updateCartItemsImage() {
        (_cartItems.value as Resource.Success).data.mapInPlace {
            if (it.cartItemImg != null) return
            viewModelScope.launch {
                when (val cartItemImage =
                    productRepository.getProductImageByProductVariantId(it.variantId)) {
                    is Resource.Success -> it.cartItemImg = Resource.Success(cartItemImage.data)
                    is Resource.Error -> Resource.Error(cartItemImage.exception)
                    is Resource.Loading -> it.cartItemImg = null
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

    fun updateCartItemsPrice() {
        (_cartItems.value as Resource.Success).data.mapInPlace {
            if (it.product != null) return
            viewModelScope.launch {
                when (val product = productRepository.getProductVariantOption(it.variantOptionId)) {
                    is Resource.Success -> it.price = product.data.price
                    is Resource.Error -> Resource.Error(product.exception)
                }
            }
            it
        }
    }

    fun onQuantityControlClick(cartItem: CartItem, value: Int) {
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