/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.utilities

import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import vn.quanprolazer.fashione.data.domain.model.ProductImage
import vn.quanprolazer.fashione.data.domain.model.Result

@BindingAdapter("cartImage")
fun ImageView.cartImage(cartImage: Result<ProductImage>?) {
    cartImage?.let {
        when (cartImage) {
            is Result.Success -> {
                this.bindImage(cartImage.data.url)
            }
            is Result.Loading -> {
            }
            else -> {
            }
        }
    }
}

@BindingAdapter("addToCartVisible")
fun Button.addToCartVisible(orderQty: LiveData<Number>?) {
    orderQty?.value?.let {
        visibility = if (orderQty.value!!.toInt() > 0) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }
}