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

    private val _productDetail = MutableLiveData<ProductDetail>()
    val productDetail : LiveData<ProductDetail> = _productDetail

//    val displayCurrentImage: LiveData<String> = Transformations.map(productDetail) {
//        "1/${productDetail.value?.images?.size}"
//    }

    private val productRepositoryImpl = ProductRepositoryImpl(ProductServiceImpl())

    init {
        viewModelScope.launch {
            _productDetail.value = productRepositoryImpl.getProductDetailByProductId(product.id)
        }

    }

}

class ProductViewModelFactory(
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