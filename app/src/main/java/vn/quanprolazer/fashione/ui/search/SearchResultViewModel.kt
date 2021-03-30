package vn.quanprolazer.fashione.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.domain.Category
import vn.quanprolazer.fashione.domain.Product
import vn.quanprolazer.fashione.domain.fromJson
import vn.quanprolazer.fashione.network.FashioneProductService
import vn.quanprolazer.fashione.network.Searcher

class SearchResultViewModel(val category: Category?, private val query: String?) : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    val textToDisplay = category?.categoryName ?: "Kết quả cho: $query"

    init {
        viewModelScope.launch {
            category?.let {
                _products.value = FashioneProductService.getProductsByCategoryId(category.categoryId)
            }

            if (!query.isNullOrEmpty()) {
                val productContainer = fromJson(Searcher.search(query).toString())
                _products.value = productContainer.products
            }
        }
    }
}