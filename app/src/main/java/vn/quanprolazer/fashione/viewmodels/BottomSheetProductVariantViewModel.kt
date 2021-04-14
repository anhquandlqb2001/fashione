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
import vn.quanprolazer.fashione.domain.model.ProductOrder
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
    private val _productOrder: MutableLiveData<ProductOrder> by lazy {
        MutableLiveData(ProductOrder(productDetail.productId))
    }
    /**
     * Variable to store Product Order to send to Cart
     */
    val productOrder: LiveData<ProductOrder> by lazy {
        _productOrder
    }

    /**
     * Function to update [_productOrder] when user change variant
     * To Update VariantName
     */
    fun onChangeVariantName(name: String) {
        _productOrder.value?.variantName = name
    }

    /**
     * Function to update [_productOrder]] when user change variant
     * To Update VariantValue (Eg: size M, L,...)
     */
    fun onChangeVariantValue(value: String) {
        _productOrder.value?.variantValue = value
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
            _productOrder.value?.qty = t
            orderQty.value = t
        }
    }
    /**
     * Function to set [orderQty] when user click increment button
     */
    fun onIncreaseQty() {
        if (orderQty.value!!.toInt() < orderLimit) {
            val t = orderQty.value!!.toInt() + 1
            _productOrder.value?.qty = t
            orderQty.value = t
        }
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