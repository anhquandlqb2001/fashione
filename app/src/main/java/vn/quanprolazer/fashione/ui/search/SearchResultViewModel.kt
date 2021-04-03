/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.domain.model.Category
import vn.quanprolazer.fashione.domain.model.Product
import vn.quanprolazer.fashione.network.repository.ProductRepositoryImpl

class SearchResultViewModel(val category: Category?, private val query: String?) : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    val textToDisplay = category?.name ?: "Kết quả cho: $query"

    init {
        viewModelScope.launch {
            category?.let {
                _products.value = ProductRepositoryImpl.getProductsByCategoryId(category.id)
            }

            if (!query.isNullOrEmpty()) {
                _products.value = ProductRepositoryImpl.findProductsByQuery(query)
            }
        }
    }

    private val _navigateToProductDetail = MutableLiveData<Product>()
    val navigateToProductDetail: LiveData<Product> = _navigateToProductDetail

    fun onClickProduct(product: Product) {
        _navigateToProductDetail.value = product
    }


    fun doneNavigate() {
        _navigateToProductDetail.value = null

    }

}