/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.viewmodels

import androidx.lifecycle.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.domain.models.*
import vn.quanprolazer.fashione.domain.repositories.ProductRepository
import vn.quanprolazer.fashione.domain.repositories.ReviewRepository


class ProductViewModel @AssistedInject constructor(
    private val productRepository: ProductRepository,
    private val reviewRepository: ReviewRepository,
    @Assisted val product: Product
) : ViewModel() {


    private val _productImages by lazy {
        val liveData = MutableLiveData<Resource<List<ProductImage>>>()
        viewModelScope.launch {
            liveData.value = productRepository.getProductImagesByProductId(product.id)
        }
        return@lazy liveData
    }

    val productImages: LiveData<Resource<List<ProductImage>>> get() = _productImages

    /**
     * Variable to store data about product
     * Encapsulation
     */
    private val _productDetail by lazy {
        val liveData = MutableLiveData<Resource<ProductDetail>>()
        viewModelScope.launch {
            liveData.value = productRepository.getProductDetailByProductId(product.id)
        }
        return@lazy liveData
    }

    /**
     * Variable to store data about product
     */
    val productDetail: LiveData<Resource<ProductDetail>> get() = _productDetail


    /**
     * Variable store data pass to SafeArgs when open BottomSheetFragmentDialog
     * Encapsulation
     */
    private val _navigateToBottomSheet: MutableLiveData<Product?> by lazy {
        MutableLiveData<Product?>()
    }

    /**
     * Variable store data pass to SafeArgs when open BottomSheetFragmentDialog
     */
    val navigateToBottomSheet: LiveData<Product?> by lazy {
        _navigateToBottomSheet
    }

    /**
     * Function to update [_navigateToBottomSheet] when user click Buy button
     */
    fun onNavigateToBottomSheet(product: Product) {
        _navigateToBottomSheet.value = product
    }


    /**
     * Function to prevent UI error after navigate to another Fragment
     * Fire after navigate Fragment
     */
    fun doneNavigate() {
        _navigateToBottomSheet.value = null
    }

    //
    private val _navigateToReview: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }

    val navigateToReview: LiveData<String?> by lazy {
        _navigateToReview
    }


    fun onNavigateToReview(productId: String) {
        _navigateToReview.value = productId
    }

    fun doneNavigateToReview() {
        _navigateToReview.value = null
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(product: Product): ProductViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory, product: Product
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(product) as T
            }
        }
    }
}

