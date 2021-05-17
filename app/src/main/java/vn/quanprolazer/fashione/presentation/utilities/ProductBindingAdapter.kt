/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.utilities

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import vn.quanprolazer.fashione.domain.models.ProductDetail
import vn.quanprolazer.fashione.domain.models.Resource

private const val PRODUCT_NAME_LIMIT = 10

@BindingAdapter("productPrice")
fun TextView.setProductPrice(productPrice: String?) {
    productPrice?.let {
        text = convertPriceStringToCurrencyString(productPrice)
    }
}

@BindingAdapter("productName")
fun TextView.setProductName(productName: String?) {
    productName?.let {
        text = productName.smartTruncate(PRODUCT_NAME_LIMIT)
    }
}

@BindingAdapter("productDescription")
fun TextView.setProductDescription(productDetail: Resource<ProductDetail>?) {
    productDetail?.let {
        when (productDetail) {
            is Resource.Success -> text = productDetail.data.description
            else -> {
            }
        }
    }
}

@BindingAdapter("variantQty")
fun TextView.setProductVariantQty(qty: Number?) {
    qty?.let {
        if (qty == -1) {
            visibility = View.INVISIBLE
        } else {
            visibility = View.VISIBLE
            text = "$qty sản phẩm"

        }
    }
}