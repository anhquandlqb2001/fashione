package vn.quanprolazer.fashione.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.domain.Category
import vn.quanprolazer.fashione.domain.Product
import vn.quanprolazer.fashione.network.FashioneCategoryService
import vn.quanprolazer.fashione.network.FashioneProductService

class SearchResultViewModel(private val categoryId: String, val query: String) : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    init {
        viewModelScope.launch {
            _products.value = FashioneProductService.getProductsByCategoryId(categoryId)
        }
    }
}