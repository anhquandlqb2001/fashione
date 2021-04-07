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
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.domain.model.Category
import vn.quanprolazer.fashione.domain.model.Product
import vn.quanprolazer.fashione.domain.model.Result
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


    private val _categories by lazy {
        val liveData = MutableLiveData<List<Category>>()
        viewModelScope.launch {
            when (val getCategoryResponse = categoryRepositoryImpl.getCategoryList()) {
                is Result.Success -> liveData.value = getCategoryResponse.data
                is Result.Error -> _exception.value = getCategoryResponse.exception
            }
        }
        return@lazy liveData
    }
    val categories: LiveData<List<Category>> = _categories

    private val _products by lazy {
        val liveData = MutableLiveData<List<Product>>()
        viewModelScope.launch {
            when (val getProductResponse = productRepositoryImpl.getProducts(Source.SERVER)) {
                is Result.Success -> liveData.value = getProductResponse.data
                is Result.Error -> _exception.value = getProductResponse.exception
            }
        }
        return@lazy liveData
    }
    val products: LiveData<List<Product>> = _products


    private val _exception = MutableLiveData<Exception>()
    val exception: LiveData<Exception> = _exception

    class SearchViewModel(private val viewModel: HomeViewModel) : BaseObservable() {
        @Bindable
        var searchText: String? = null
            set(value) {
                viewModel.searchText.value = value
                field = value
            }
    }
}


