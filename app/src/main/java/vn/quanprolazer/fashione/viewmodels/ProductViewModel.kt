/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.data.domain.model.Product
import vn.quanprolazer.fashione.data.domain.model.ProductDetail
import vn.quanprolazer.fashione.data.domain.repository.ProductRepository
import vn.quanprolazer.fashione.data.network.repository.ProductRepositoryImpl
import vn.quanprolazer.fashione.data.network.service.ProductServiceImpl

class ProductViewModel(val product: Product) : ViewModel() {

    private val productRepositoryImpl: ProductRepository by lazy {
        ProductRepositoryImpl(ProductServiceImpl())
    }

    /**
     * Variable to store data about product
     * Encapsulation
     */
    private val _productDetail by lazy {
        val liveData = MutableLiveData<ProductDetail>()
        viewModelScope.launch {
            liveData.value = productRepositoryImpl.getProductDetailByProductId(product.id)
        }
        return@lazy liveData
    }
    /**
     * Variable to store data about product
     */
    val productDetail: LiveData<ProductDetail> by lazy {
        _productDetail
    }

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
        product.detail = productDetail.value!!

        _navigateToBottomSheet.value = product
    }

    /**
     * Function to prevent UI error after navigate to another Fragment
     * Fire after navigate Fragment
     */
    fun doneNavigate() {
        _navigateToBottomSheet.value = null
    }

    class Factory(
        private val product: Product
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
                return ProductViewModel(product) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}

