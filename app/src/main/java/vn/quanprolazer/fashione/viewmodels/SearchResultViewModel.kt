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

    val textToDisplay = category?.name ?: "Kết quả cho: $query"

    private val productRepositoryImpl = ProductRepositoryImpl(ProductServiceImpl())

    private val _products by lazy {
        val liveData = MutableLiveData<List<Product>>()
        viewModelScope.launch {
            category?.let {
                liveData.value = productRepositoryImpl.getProductsByCategoryId(category.id)
            }

            if (!query.isNullOrEmpty()) {
                liveData.value = productRepositoryImpl.findProductsByQuery(query)
            }
        }
        return@lazy liveData
    }
    val products: LiveData<List<Product>> = _products


    private val _navigateToProductDetail = MutableLiveData<Product?>()
    val navigateToProductDetail: LiveData<Product?> = _navigateToProductDetail

    fun onClickProduct(product: Product) {
        _navigateToProductDetail.value = product
    }


    fun doneNavigate() {
        _navigateToProductDetail.value = null

    }

    class Factory(
        private val category: Category?, private val query: String?
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchResultViewModel::class.java)) {
                return SearchResultViewModel(category, query) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
