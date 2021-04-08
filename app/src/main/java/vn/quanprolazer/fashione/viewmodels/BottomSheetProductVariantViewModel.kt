/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import timber.log.Timber
import vn.quanprolazer.fashione.domain.model.ProductDetail

class BottomSheetProductVariantViewModel(val productDetail: LiveData<ProductDetail>) : ViewModel() {

    init {
        Timber.i(productDetail.toString())
        Timber.i("here")
    }

    class Factory(
        private val productDetail: LiveData<ProductDetail>
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