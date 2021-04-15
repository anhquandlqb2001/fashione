/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import vn.quanprolazer.fashione.domain.model.ProductDetail
import vn.quanprolazer.fashione.domain.model.CartItem
import vn.quanprolazer.fashione.domain.model.ProductVariant

class BottomSheetProductVariantViewModel(private val productDetail: ProductDetail) : ViewModel() {

    /**
     * Variable to store available Product Variants
     * Encapsulation
     */
    private val _productVariants: MutableLiveData<List<ProductVariant>> by lazy {
        MutableLiveData<List<ProductVariant>>(productDetail.variants)
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
        MutableLiveData(CartItem(productDetail.productId))
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
            _cartItem.value?.qty = t
            orderQty.value = t
        }
    }

    /**
     * Function to set [orderQty] when user click increment button
     */
    fun onIncreaseQty() {
        if (orderQty.value!!.toInt() < orderLimit) {
            val t = orderQty.value!!.toInt() + 1
            _cartItem.value?.qty = t
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
    fun setVariantPrice(price: String) {
        _variantPrice.value = price
    }


    /**
     * Function to call when user click Add to cart button
     * Call api to save cart item -> cart
     */
    fun onClickAddToCart() {

    }


    class Factory(
        private val productDetail: ProductDetail
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BottomSheetProductVariantViewModel::class.java)) {
                return BottomSheetProductVariantViewModel(productDetail) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}