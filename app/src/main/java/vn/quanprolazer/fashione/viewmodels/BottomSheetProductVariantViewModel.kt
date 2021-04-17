/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import vn.quanprolazer.fashione.data.domain.model.CartItem
import vn.quanprolazer.fashione.data.domain.model.Product
import vn.quanprolazer.fashione.data.domain.model.ProductVariant
import vn.quanprolazer.fashione.data.domain.repository.ProductRepository
import vn.quanprolazer.fashione.data.domain.repository.UserRepository
import vn.quanprolazer.fashione.data.network.repository.OrderRepositoryImpl
import vn.quanprolazer.fashione.data.network.repository.ProductRepositoryImpl
import vn.quanprolazer.fashione.data.network.service.OrderServiceImpl
import vn.quanprolazer.fashione.data.network.service.ProductServiceImpl

class BottomSheetProductVariantViewModel(private val product: Product) : ViewModel() {

    private val productRepositoryImpl: ProductRepository by lazy {
        ProductRepositoryImpl(ProductServiceImpl())
    }

    private val userRepositoryImpl: UserRepository by lazy {
        UserRepository()
    }

    private val _user: UserRepository.FirebaseUserLiveData by lazy {
        userRepositoryImpl.getUser()
    }

    /**
     * Variable to store available Product Variants
     * Encapsulation
     */
    private val _productVariants: MutableLiveData<List<ProductVariant>> by lazy {
        val liveData = MutableLiveData<List<ProductVariant>>()
        viewModelScope.launch {
            liveData.value =
                productRepositoryImpl.getProductVariantsAndOptionsByProductId(product.id)

        }
        return@lazy liveData
    }

    /**
     * Variable to store available Product Variants
     */
    val productVariants: LiveData<List<ProductVariant>> by lazy {
        _productVariants
    }


    /**
     * Variable to store ProductOrder to send to Cart
     * Encapsulation
     */
    private val _cartItem: MutableLiveData<CartItem> by lazy {
        MutableLiveData(CartItem(product.id, _user.value?.uid!!))
    }

    /**
     * Variable to store Product Order to send to Cart
     */
    val cartItem: LiveData<CartItem> by lazy {
        _cartItem
    }

    /**
     * Function to update [_cartItem] when user change variant
     * To Update VariantName
     */
    fun onChangeVariantName(name: String) {
        _cartItem.value?.variantName = name
    }

    /**
     * Function to update [_cartItem]] when user change variant
     * To Update VariantValue (Eg: size M, L,...)
     */
    fun onChangeVariantValue(value: String) {
        _cartItem.value?.variantValue = value
    }

    /**
     * Limit order quantity value
     */
    private var orderLimit = 0

    /**
     * Function to set [orderLimit]
     */
    fun setProductOrderValueLimit(value: Number) {
        orderLimit = value as Int
    }

    /**
     * Livedata val to store current order qty user choose
     */
    val orderQty: MutableLiveData<Number> by lazy {
        MutableLiveData(0)
    }

    /**
     * Function to reset [orderQty] when user change variant
     */
    fun resetOrderQty() {
        orderQty.value = 0
    }

    /**
     * Function to set [orderQty] when user click decrement button
     */
    fun onDecreaseQty() {
        if (orderQty.value!!.toInt() > 0) {
            val t = orderQty.value!!.toInt() - 1
            _cartItem.value?.quantity = t
            orderQty.value = t
        }
    }

    /**
     * Function to set [orderQty] when user click increment button
     */
    fun onIncreaseQty() {
        if (orderQty.value!!.toInt() < orderLimit) {
            val t = orderQty.value!!.toInt() + 1
            _cartItem.value?.quantity = t
            orderQty.value = t
        }
    }

    /**
     * Variable to store current price of variant
     * To store to [_cartItem]
     *
     * Encapsulation
     */
    private val _variantPrice: MutableLiveData<String> by lazy {
        MutableLiveData("0")
    }

    /**
     * Variable to store current price of variant
     * To store to [_cartItem]
     */
    val variantPrice: LiveData<String> by lazy {
        _variantPrice
    }

    /**
     * Function to update variant price, when user choose variant value
     */
    fun updateVariantPrice(price: String) {
        _variantPrice.value = price
    }

    /**
     * Function to update variant id, when user choose variant value
     */
    fun updateCartItemVariantId(variantId: String, variantOptionId: String) {
        _cartItem.value?.variantId = variantId
        _cartItem.value?.variantOptionId = variantOptionId
    }


    /**
     * Function to call when user click Add to cart button
     * Call api to save cart item -> cart
     */
    fun onClickAddToCart() {
        updateCartItemPrice()
        Timber.i(cartItem.value.toString())

        viewModelScope.launch(Dispatchers.Default) {
            try {
                OrderRepositoryImpl(OrderServiceImpl()).addToCart(cartItem.value!!, _user.value?.uid!!)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    /**
     * Function to call when user click Add to cart button
     * Update [_cartItem] price value
     */
    private fun updateCartItemPrice() {
        _cartItem.value?.price = _variantPrice.value.toString()
    }


    class Factory(
        private val product: Product
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BottomSheetProductVariantViewModel::class.java)) {
                return BottomSheetProductVariantViewModel(product) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}