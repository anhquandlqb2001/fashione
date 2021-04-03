/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.ui.product

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.domain.model.Product
import vn.quanprolazer.fashione.domain.model.ProductDetail
import vn.quanprolazer.fashione.network.repository.ProductRepositoryImpl

class ProductViewModel(val product: Product) : ViewModel() {

    private val _productDetail = MutableLiveData<ProductDetail>()
    val productDetail : LiveData<ProductDetail> = _productDetail

//    val displayCurrentImage: LiveData<String> = Transformations.map(productDetail) {
//        "1/${productDetail.value?.images?.size}"
//    }

    init {
        viewModelScope.launch {
            _productDetail.value = ProductRepositoryImpl.getProductDetailByProductId(product.id)
        }

    }

}