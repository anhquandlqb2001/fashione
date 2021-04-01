/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.ui.product

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.domain.Product
import vn.quanprolazer.fashione.domain.ProductDetail
import vn.quanprolazer.fashione.network.FashioneProductService

private const val TAG = "ProductViewModel"

class ProductViewModel(val product: Product) : ViewModel() {

    private val _productDetail = MutableLiveData<ProductDetail>()
    val productDetail : LiveData<ProductDetail> = _productDetail

//    val displayCurrentImage: LiveData<String> = Transformations.map(productDetail) {
//        "1/${productDetail.value?.images?.size}"
//    }

    init {
        viewModelScope.launch {
            _productDetail.value = FashioneProductService.getProductDetailByProductId(product.id)
//            Log.i(TAG, FashioneProductService.getProductDetailByProductId(product.id).toString())

        }

    }

}