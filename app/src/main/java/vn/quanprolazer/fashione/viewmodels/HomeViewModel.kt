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
import vn.quanprolazer.fashione.data.domain.model.Category
import vn.quanprolazer.fashione.data.domain.model.Product
import vn.quanprolazer.fashione.data.domain.model.Result
import vn.quanprolazer.fashione.data.domain.repository.CategoryRepository
import vn.quanprolazer.fashione.data.domain.repository.ProductRepository
import vn.quanprolazer.fashione.data.network.repository.CategoryRepositoryImpl
import vn.quanprolazer.fashione.data.network.repository.ProductRepositoryImpl
import vn.quanprolazer.fashione.data.network.service.CategoryServiceImpl
import vn.quanprolazer.fashione.data.network.service.ProductServiceImpl


class HomeViewModel : ViewModel() {


    private val categoryRepositoryImpl: CategoryRepository by lazy {
        CategoryRepositoryImpl(CategoryServiceImpl())
    }
    private val productRepositoryImpl: ProductRepository by lazy {
        ProductRepositoryImpl(ProductServiceImpl())
    }

    /**
     * Variable to store list of available category
     * Data for display Category RecycleView
     *
     * Encapsulation
     */
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

    /**
     * Variable to store list of available category
     * Data for display Category RecycleView
     */
    val categories: LiveData<List<Category>> by lazy {
        _categories
    }

    /**
     * Variable to store list of available product
     * Data for display Product RecycleView
     *
     * Encapsulation
     */
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

    /**
     * Variable to store list of available product
     * Data for display Product RecycleView
     */
    val products: LiveData<List<Product>> by lazy {
        _products
    }

    /**
     * Variable to store exception when get data from repository
     * Can be null
     *
     * Encapsulation
     */
    private val _exception: MutableLiveData<Exception> by lazy {
        MutableLiveData<Exception>()
    }

    /**
     * Variable to store exception when get data from repository
     * Can be null
     */
    val exception: LiveData<Exception> by lazy {
        _exception
    }

    /*------------------------------------FOR NAVIGATION----------------------------------*/


    /**
     * Variable to store text user input in EditText
     * This also use for 2-ways binding
     */
    val searchText: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }


    /**
     * Variable store data pass to SafeArgs when Navigate to SearchResultFragment by click to CATEGORY
     * Encapsulation
     */
    private val _navigateToSearchResultByCategory: MutableLiveData<Category> by lazy {
        MutableLiveData<Category>()
    }

    /**
     * Variable store data pass to SafeArgs when Navigate to SearchResultFragment by click to CATEGORY
     */
    val navigateToSearchResultByCategory: LiveData<Category> by lazy {
        _navigateToSearchResultByCategory
    }

    /**
     * Function to update [_navigateToSearchResultByCategory] when user click categories item
     */
    fun onClickCategory(category: Category) {
        _navigateToSearchResultByCategory.value = category
    }


    /**
     * Variable store data pass to SafeArgs when Navigate to SearchResultFragment by SEARCH TEXT
     * Encapsulation
     */
    private val _navigateToSearchResultByText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    /**
     * Variable store data pass to SafeArgs when Navigate to SearchResultFragment by SEARCH TEXT
     */
    val navigateToSearchResultByText: LiveData<String> by lazy {
        _navigateToSearchResultByText
    }

    /**
     * Function to update [_navigateToSearchResultByText] when event ENTER fire
     */
    fun onSearchByText() {
        _navigateToSearchResultByText.value = searchText.value
    }


    /**
     * Variable store data pass to SafeArgs when Navigate to ProductFragment
     * Encapsulation
     */
    private val _navigateToProductDetail: MutableLiveData<Product> by lazy {
        MutableLiveData<Product>()
    }

    /**
     * Variable store data pass to SafeArgs when Navigate to ProductFragment
     */
    val navigateToProductDetail: LiveData<Product> by lazy {
        _navigateToProductDetail
    }

    /**
     * Function to update [_navigateToProductDetail] when user click to product item
     */
    fun onClickProduct(product: Product) {
        _navigateToProductDetail.value = product
    }

    /**
     * Function to handle UI err
     * Use after navigate to another Fragment
     */
    fun doneNavigate() {
        _navigateToSearchResultByCategory.value = null
        _navigateToSearchResultByText.value = null
        _navigateToProductDetail.value = null
    }
}


