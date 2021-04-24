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
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.adapters.CartItemAdapter
import vn.quanprolazer.fashione.data.domain.model.*

@BindingAdapter("cartImage")
fun ImageView.cartImage(cartImage: Resource<ProductImage>?) {
    cartImage?.let {
        when (cartImage) {
            is Resource.Success -> {
                this.loadImage(cartImage.data.url)
            }
            is Resource.Loading -> {
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

@BindingAdapter("cartItemName")
fun TextView.cartItemName(product: Resource<Product>?) {
    product?.let {
        when(product) {
            is Resource.Success -> {
                setProductName(product.data.name)
            }
            else -> {}
        }
    }
}

@BindingAdapter("cartItemQuantity")
fun TextView.cartItemQuantity(cartItem: CartItem?) {
    cartItem?.let {
        text = cartItem.quantity.toString()
    }
}

@BindingAdapter("cartItems")
fun RecyclerView.setCartItems(cartItems: LiveData<Resource<List<CartItem>>>?) {
    cartItems?.let {
        when(it.value) {
            is Resource.Success -> (this.adapter as CartItemAdapter).submitList((it.value as Resource.Success<List<CartItem>>).data.toMutableList())
            else -> {}
        }
    }
}

@BindingAdapter("totalPrice")
fun TextView.calculateTotalPrice(orderData: LiveData<OrderData>?) {
    orderData?.value?.let {
        Timber.i(orderData.value.toString())

        val priceText = orderData.value!!.totalPrice
        val priceCurrency = convertPriceStringToCurrencyString(priceText)

        text = resources.getString(R.string.total_price_text, priceCurrency)
        return
    }

    text = resources.getString(R.string.total_price_text, convertPriceStringToCurrencyString("0"))
}