/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.domain.model.Category
import vn.quanprolazer.fashione.domain.model.Product
import vn.quanprolazer.fashione.network.repository.ProductRepositoryImpl
import vn.quanprolazer.fashione.network.service.ProductServiceImpl

class SearchResultViewModel(val category: Category?, private val query: String?) : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    val textToDisplay = category?.name ?: "Kết quả cho: $query"

    private val productRepositoryImpl = ProductRepositoryImpl(ProductServiceImpl())

    init {
        viewModelScope.launch {
            category?.let {
                _products.value = productRepositoryImpl.getProductsByCategoryId(category.id)
            }

            if (!query.isNullOrEmpty()) {
                _products.value = productRepositoryImpl.findProductsByQuery(query)
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

class SearchResultViewModelFactory(
    private val category: Category?, private val query: String?) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchResultViewModel::class.java)) {
            return SearchResultViewModel(category, query) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}