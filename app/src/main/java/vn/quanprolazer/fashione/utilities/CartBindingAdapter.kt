/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.utilities

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import vn.quanprolazer.fashione.data.domain.model.ProductImage
import vn.quanprolazer.fashione.data.domain.model.Result

@BindingAdapter("cartImage")
fun ImageView.cartImage(cartImage: Result<ProductImage>?) {
    cartImage?.let {
        when (cartImage) {
            is Result.Success -> {
                this.loadImage(cartImage.data.url)
            }
            is Result.Loading -> {
            }
            else -> {
            }
        }
    }
}

@BindingAdapter("addToCartVisible")
fun Button.addToCartVisible(orderQty: Number?) {
    orderQty?.let {
        visibility = if (orderQty.toInt() > 0) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }
}

@BindingAdapter(value = ["variantName", "variantValue"])
fun TextView.variantOptionName(variantName: String?, variantValue: String?) {
    if (variantName == null || variantValue == null) return

    val texToDisplay = "Loại: $variantName - $variantValue"

    text = texToDisplay.smartTruncate(20)

}