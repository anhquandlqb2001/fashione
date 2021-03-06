/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */


package vn.quanprolazer.fashione.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.domain.models.Category
import vn.quanprolazer.fashione.domain.models.Product
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.repositories.CategoryRepository
import vn.quanprolazer.fashione.domain.repositories.ProductRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    /**
     * Variable to store list of available category
     * Data for display Category RecycleView
     *
     * Encapsulation
     */
    private val _categories: MutableLiveData<Resource<List<Category>>> by lazy {
        val liveData = MutableLiveData<Resource<List<Category>>>(Resource.Loading)
        viewModelScope.launch {
            liveData.value = categoryRepository.getCategoryList()
        }
        return@lazy liveData
    }

    /**
     * Variable to store list of available category
     * Data for display Category RecycleView
     */
    val categories: LiveData<Resource<List<Category>>> get() = _categories

    /**
     * Variable to store if next recent product is exist
     */
    private var _recentProductDocumentId: String? = null
    val recentProductDocumentId: String? get() = _recentProductDocumentId
    fun updateNextRecentProductDocumentId(id: String?) {
        _recentProductDocumentId = id
    }

    /**
     * Variable to store list of available product
     * Data for display Product RecycleView
     *
     * Encapsulation
     */
    private val _recentProducts: MutableLiveData<Resource<MutableList<Product>>>
            by lazy { MutableLiveData<Resource<MutableList<Product>>>() }

    /**
     * Variable to store list of available product
     * Data for display Product RecycleView
     */
    val recentProducts: LiveData<Resource<MutableList<Product>>> get() = _recentProducts

    fun fetchRecentProduct() {
        _recentProducts.value = Resource.Loading
        viewModelScope.launch {
            when (val data = productRepository.getRecentProducts(null)) {
                is Resource.Success -> {
                    _recentProducts.value = Resource.Success(data.data.products)
                    updateNextRecentProductDocumentId(data.data.lastVisibleId)
                }
                is Resource.Error -> {
                    _recentProducts.value = Resource.Error(data.exception)
                    updateNextRecentProductDocumentId(null)
                }
            }
        }
    }

    private val _newRecentProducts: MutableLiveData<Resource<MutableList<Product>>>
            by lazy { MutableLiveData<Resource<MutableList<Product>>>() }
    val newRecentProducts: LiveData<Resource<MutableList<Product>>> get() = _newRecentProducts


    fun fetchMoreRecentlyProducts() {
        if (_recentProductDocumentId.isNullOrBlank()) return
        _newRecentProducts.value = Resource.Loading
        viewModelScope.launch {
            when (val data = productRepository.getRecentProducts(_recentProductDocumentId)) {
                is Resource.Success -> {
                    _newRecentProducts.value = Resource.Success(data.data.products)
                    updateNextRecentProductDocumentId(data.data.lastVisibleId)
                }
                is Resource.Error -> {
                    _newRecentProducts.value = Resource.Error(data.exception)
                    updateNextRecentProductDocumentId(null)
                }
            }
        }
    }


    private val _highRateProducts: MutableLiveData<Resource<List<Product>>>
            by lazy { MutableLiveData<Resource<List<Product>>>() }

    val highRateProducts: LiveData<Resource<List<Product>>> get() = _highRateProducts

    fun fetchHighRateProduct() {
        _highRateProducts.value = Resource.Loading
        viewModelScope.launch {
            _highRateProducts.value = productRepository.getHighRatingProducts()
        }
    }

    private val _highViewProducts: MutableLiveData<Resource<List<Product>>>
            by lazy { MutableLiveData<Resource<List<Product>>>() }

    val highViewProducts: LiveData<Resource<List<Product>>> get() = _highViewProducts

    fun fetchHighViewProduct() {
        _highViewProducts.value = Resource.Loading
        viewModelScope.launch {
            _highViewProducts.value = productRepository.getHighViewProducts()
        }
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
        logAnalyticsEvent(product.id)
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


    /* Analytics */
    private val firebaseAnalytics = Firebase.analytics

    /**
     * Logs an event in Firebase Analytics that is used in aggregate to train the recommendations
     * model.
     */
    private fun logAnalyticsEvent(id: String) {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
            param(FirebaseAnalytics.Param.ITEM_ID, id)
        }
    }
}


