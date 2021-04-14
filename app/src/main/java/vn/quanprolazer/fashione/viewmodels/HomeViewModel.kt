/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */


package vn.quanprolazer.fashione.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.Source
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.domain.model.Category
import vn.quanprolazer.fashione.domain.model.Product
import vn.quanprolazer.fashione.domain.model.Result
import vn.quanprolazer.fashione.domain.repository.UserRepository
import vn.quanprolazer.fashione.network.repository.CategoryRepositoryImpl
import vn.quanprolazer.fashione.network.repository.ProductRepositoryImpl
import vn.quanprolazer.fashione.network.service.CategoryServiceImpl
import vn.quanprolazer.fashione.network.service.ProductServiceImpl


class HomeViewModel : ViewModel() {

    private val _navigateToSearchResultByCategory: MutableLiveData<Category> by lazy {
        MutableLiveData<Category>()
    }
    val navigateToSearchResultByCategory: LiveData<Category> by lazy {
        _navigateToSearchResultByCategory
    }

    fun onClickCategory(category: Category) {
        _navigateToSearchResultByCategory.value = category
    }

    private val _navigateToSearchResultByText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val navigateToSearchResultByText: LiveData<String> by lazy {
        _navigateToSearchResultByText
    }


    val searchText: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }

    fun onSearch() {
        _navigateToSearchResultByText.value = searchText.value
    }

    private val _navigateToProductDetail: MutableLiveData<Product> by lazy {
        MutableLiveData<Product>()
    }
    val navigateToProductDetail: LiveData<Product> by lazy {
        _navigateToProductDetail
    }

    fun onClickProduct(product: Product) {
        _navigateToProductDetail.value = product
    }


    fun doneNavigate() {
        _navigateToSearchResultByCategory.value = null
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
    val categories: LiveData<List<Category>> by lazy {
        _categories
    }

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
    val products: LiveData<List<Product>> by lazy {
        _products
    }


    private val _exception: MutableLiveData<Exception> by lazy {
        MutableLiveData<Exception>()
    }
    val exception: LiveData<Exception> by lazy {
        _exception
    }

    private val userRepository = UserRepository()

    val user = userRepository.getUser()

}


