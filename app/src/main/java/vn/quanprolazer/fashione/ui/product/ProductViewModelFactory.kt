/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import vn.quanprolazer.fashione.domain.Product

class ProductViewModelFactory(
    private val product: Product) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
                return ProductViewModel(product) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}