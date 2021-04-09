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
import vn.quanprolazer.fashione.domain.model.ProductVariant

class BottomSheetProductVariantViewModel(variants: List<ProductVariant>) : ViewModel() {

    private val _productVariants = MutableLiveData<List<ProductVariant>>()

    val productVariants: LiveData<List<ProductVariant>> get() = _productVariants

    init {
        _productVariants.value = variants
    }

    class Factory(
        private val variants: List<ProductVariant>
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BottomSheetProductVariantViewModel::class.java)) {
                return BottomSheetProductVariantViewModel(variants) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}