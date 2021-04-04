/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */


package vn.quanprolazer.fashione.viewmodels

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.Source
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.domain.model.Category
import vn.quanprolazer.fashione.domain.model.Product
import vn.quanprolazer.fashione.network.repository.CategoryRepositoryImpl
import vn.quanprolazer.fashione.network.repository.ProductRepositoryImpl
import vn.quanprolazer.fashione.network.service.CategoryServiceImpl
import vn.quanprolazer.fashione.network.service.ProductServiceImpl


class HomeViewModel : ViewModel() {

    private val _navigateToSearchResult = MutableLiveData<Category>()
    val navigateToSearchResult: LiveData<Category> = _navigateToSearchResult

    fun onClickCategory(category: Category) {
        _navigateToSearchResult.value = category
    }


    private val _navigateToSearchResultByText = MutableLiveData<String>()
    val navigateToSearchResultByText: LiveData<String> = _navigateToSearchResultByText


    val searchText = MutableLiveData<String>()

    fun onSearch() {
        _navigateToSearchResultByText.value = searchText.value
    }

    private val _navigateToProductDetail = MutableLiveData<Product>()
    val navigateToProductDetail: LiveData<Product> = _navigateToProductDetail

    fun onClickProduct(product: Product) {
        _navigateToProductDetail.value = product
    }


    fun doneNavigate() {
        _navigateToSearchResult.value = null
        _navigateToSearchResultByText.value = null
        _navigateToProductDetail.value = null

    }

    private val categoryRepositoryImpl = CategoryRepositoryImpl(CategoryServiceImpl())

    private val productRepositoryImpl = ProductRepositoryImpl(ProductServiceImpl())

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    init {
        viewModelScope.launch {
            _categories.value = categoryRepositoryImpl.getCategoryListFromLocal()
            _products.value = productRepositoryImpl.getProducts(Source.SERVER)

            _categories.value = categoryRepositoryImpl.getCategoryList()
        }
    }
}


class SearchViewModel(private val viewModel: HomeViewModel) : BaseObservable() {
    @Bindable
    var searchText: String? = null
        set(value) {
            viewModel.searchText.value = value
            field = value
        }
}