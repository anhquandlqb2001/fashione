/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.domain.model.Product
import vn.quanprolazer.fashione.domain.model.ProductDetail
import vn.quanprolazer.fashione.network.repository.ProductRepositoryImpl
import vn.quanprolazer.fashione.network.service.ProductServiceImpl

class ProductViewModel(val product: Product) : ViewModel() {

    private val productRepositoryImpl = ProductRepositoryImpl(ProductServiceImpl())

    private val _productDetail by lazy {
        val liveData = MutableLiveData<ProductDetail>()
        viewModelScope.launch {
            liveData.value = productRepositoryImpl.getProductDetailByProductId(product.id)
        }
        return@lazy liveData
    }
    val productDetail: LiveData<ProductDetail> = _productDetail


    private val _navigateToBottomSheet = MutableLiveData<ProductDetail>()
    val navigateToBottomSheet: LiveData<ProductDetail> get() = _navigateToBottomSheet

    fun onNavigateToBottomSheet(productDetail: ProductDetail) {
        _navigateToBottomSheet.value = productDetail
    }

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

