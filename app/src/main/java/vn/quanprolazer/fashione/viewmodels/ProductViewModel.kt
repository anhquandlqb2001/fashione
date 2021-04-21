/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import androidx.lifecycle.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.data.domain.model.Product
import vn.quanprolazer.fashione.data.domain.model.ProductDetail
import vn.quanprolazer.fashione.data.domain.model.ProductImage
import vn.quanprolazer.fashione.data.domain.repository.ProductRepository


class ProductViewModel @AssistedInject constructor(private val productRepository: ProductRepository,
                                                   @Assisted val product: Product
) : ViewModel() {


    private val _productImages by lazy {
        val liveData = MutableLiveData<List<ProductImage>>()
        viewModelScope.launch {
            liveData.value = productRepository.getProductImagesByProductId(product.id)
        }
        return@lazy liveData
    }

    val productImages: LiveData<List<ProductImage>> get() = _productImages

    /**
     * Variable to store data about product
     * Encapsulation
     */
    private val _productDetail by lazy {
        val liveData = MutableLiveData<ProductDetail>()
        viewModelScope.launch {
            liveData.value = productRepository.getProductDetailByProductId(product.id)
        }
        return@lazy liveData
    }

    /**
     * Variable to store data about product
     */
    val productDetail: LiveData<ProductDetail> get() = _productDetail


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

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(product: Product): ProductViewModel
    }

    companion object {
        fun provideFactory(assistedFactory: AssistedFactory, product: Product
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(product) as T
            }
        }
    }
}

