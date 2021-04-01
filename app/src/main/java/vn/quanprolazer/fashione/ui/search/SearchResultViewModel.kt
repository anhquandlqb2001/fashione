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
import vn.quanprolazer.fashione.domain.Category
import vn.quanprolazer.fashione.domain.Product
import vn.quanprolazer.fashione.network.FashioneProductService
import vn.quanprolazer.fashione.network.NetworkAlgoliaProductContainer
import vn.quanprolazer.fashione.network.Searcher
import vn.quanprolazer.fashione.network.asDomainProduct

class SearchResultViewModel(val category: Category?, private val query: String?) : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    val textToDisplay = category?.name ?: "Kết quả cho: $query"

    init {
        viewModelScope.launch {
            category?.let {
                _products.value = FashioneProductService.getProductsByCategoryId(category.id)
            }

            if (!query.isNullOrEmpty()) {
                val productContainer = NetworkAlgoliaProductContainer.fromJson(Searcher.search(query).toString())
                _products.value = productContainer.products.map { it.asDomainProduct() }
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