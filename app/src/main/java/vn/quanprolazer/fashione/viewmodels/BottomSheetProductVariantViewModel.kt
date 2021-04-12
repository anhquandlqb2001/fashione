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
import timber.log.Timber
import vn.quanprolazer.fashione.domain.model.ProductDetail
import vn.quanprolazer.fashione.domain.model.ProductOrder
import vn.quanprolazer.fashione.domain.model.ProductVariant

class BottomSheetProductVariantViewModel(productDetail: ProductDetail) : ViewModel() {

    private val _productVariants = MutableLiveData<List<ProductVariant>>()

    val productVariants: LiveData<List<ProductVariant>> get() = _productVariants

    private val _productOrder = MutableLiveData(ProductOrder(productDetail.productId))
    val productOrder: LiveData<ProductOrder> get() = _productOrder

    fun onChangeVariantName(name: String) {
        _productOrder.value?.variantName = name
    }

    fun onChangeVariantValue(value: String) {
        _productOrder.value?.variantValue = value
    }

    private var orderLimit = 0

    val orderQty = MutableLiveData<Number>(0)

    fun setProductOrderValueLimit(value: Number) {
        orderLimit = value as Int
    }

    fun onDecreaseQty() {
        if (_productOrder.value?.qty?.toInt()!! > 0) {
            val t = _productOrder.value?.qty?.toInt()!! - 1
            _productOrder.value?.qty = t
            orderQty.value = t
        }
    }

    fun onIncreaseQty() {
        if (_productOrder.value?.qty?.toInt()!! < orderLimit) {
            val t = _productOrder.value?.qty?.toInt()!! + 1
            _productOrder.value?.qty = t
            orderQty.value = t
        }
    }


    init {
        _productVariants.value = productDetail.variants
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