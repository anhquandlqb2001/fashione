package vn.quanprolazer.fashione.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.domain.Category
import vn.quanprolazer.fashione.domain.Product
import vn.quanprolazer.fashione.network.FashioneCategoryService
import vn.quanprolazer.fashione.network.FashioneProductService

class SearchResultViewModel(val category: Category?, private val query: String?) : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    init {
        viewModelScope.launch {
            category?.let {
                _products.value = FashioneProductService.getProductsByCategoryId(category.categoryId)
            }

            query?.let {
                Log.i("ViewMode", query.toString())
                _products.value = FashioneProductService.searchProductByQuery(query)
            }
        }
    }
}