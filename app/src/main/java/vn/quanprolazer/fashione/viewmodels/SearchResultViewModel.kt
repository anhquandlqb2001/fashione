/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import androidx.lifecycle.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.data.domain.model.Category
import vn.quanprolazer.fashione.data.domain.model.Product
import vn.quanprolazer.fashione.data.domain.model.PurchaseToAddReview
import vn.quanprolazer.fashione.data.domain.model.Resource
import vn.quanprolazer.fashione.data.domain.repository.ProductRepository
import vn.quanprolazer.fashione.data.network.repository.ProductRepositoryImpl
import vn.quanprolazer.fashione.data.network.service.ProductServiceImpl

class SearchResultViewModel @AssistedInject constructor(
    private val productRepository: ProductRepository,
    @Assisted val category: Category?,
    @Assisted private val query: String?
) : ViewModel() {

    /**
     * Variable to display header text of screen
     * Eg: Man, Woman, Result for: jean,...
     */
    val textToDisplay = category?.name ?: "Kết quả cho: $query"


    /**
     * Variable to store list of available product
     * Data for display Product RecycleView
     *
     * Encapsulation
     */
    private val _products by lazy {
        val liveData = MutableLiveData<Resource<List<Product>>>()
        viewModelScope.launch {
            category?.let {
                liveData.value = productRepository.getProductsByCategoryId(category.id)
            }

            if (!query.isNullOrEmpty()) {
                liveData.value = productRepository.findProductsByQuery(query)
            }
        }
        return@lazy liveData
    }

    /**
     * Variable to store list of available product
     * Data for display Product RecycleView
     */
    val products: LiveData<Resource<List<Product>>> get() = _products

    /**
     * Variable store data pass to SafeArgs when Navigate to ProductFragment
     * Encapsulation
     */
    private val _navigateToProductDetail: MutableLiveData<Product?> by lazy {
        MutableLiveData<Product?>()
    }

    /**
     * Variable store data pass to SafeArgs when Navigate to ProductFragment
     */
    val navigateToProductDetail: LiveData<Product?> by lazy {
        _navigateToProductDetail
    }

    /**
     * Function to update [_navigateToProductDetail] when user click to product item
     */
    fun onClickProduct(product: Product) {
        _navigateToProductDetail.value = product
    }

    /**
     * Function to prevent UI err
     * Use after navigate to another Fragment
     */
    fun doneNavigate() {
        _navigateToProductDetail.value = null
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(category: Category?, query: String?): SearchResultViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory, category: Category?, query: String?
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(category, query) as T
            }
        }
    }
}
