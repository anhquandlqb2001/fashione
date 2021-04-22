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
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.bumptech.glide.request.RequestOptions
import vn.quanprolazer.fashione.GlideApp
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.data.domain.model.ProductDetail
import vn.quanprolazer.fashione.data.domain.model.ProductImage
import vn.quanprolazer.fashione.data.domain.model.Result

private const val PRODUCT_NAME_LIMIT = 10


@BindingAdapter("productPrice")
fun setProductPrice(view: TextView, productPrice: String?) {
    productPrice?.let {
        view.text = convertPriceStringToCurrencyString(productPrice)
    }
}

@BindingAdapter("productName")
fun setProductName(view: TextView, productName: String?) {
    productName?.let {
        view.text = productName.smartTruncate(PRODUCT_NAME_LIMIT)
    }
}


@BindingAdapter("imageUrl")
fun bindImage(view: ImageView, imageUrl: String?) {
    imageUrl?.let {
        val imgUri = imageUrl.toUri().buildUpon().scheme("https").build()
        GlideApp.with(view.context).load(imgUri).apply(
            RequestOptions().placeholder(R.drawable.loading_anim).error(R.drawable.ic_broken)
        ).into(view)
    }
}

@BindingAdapter("variantQty")
fun setProductVariantQty(view: TextView, qty: Number?) {
    qty?.let {
        if (qty == -1) {
            view.visibility = View.INVISIBLE
        } else {
            view.visibility = View.VISIBLE
            view.text = "$qty sản phẩm"

        }
    }
}

@BindingAdapter("orderQty")
fun setOrderQty(view: TextView, orderQty: LiveData<Number>?) {
    orderQty?.value?.let {
        view.text = orderQty.value?.toString()
    }
}

@BindingAdapter("addToCartVisible")
fun addToCartVisible(view: Button, orderQty: LiveData<Number>?) {
    orderQty?.value?.let {
        if (orderQty.value!!.toInt() > 0) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.INVISIBLE
        }
    }
}

@BindingAdapter(value = ["variantQty", "variantPrice"], requireAll = false)
fun setTotalPrice(view: TextView, orderQty: LiveData<Number>?, variantPrice: LiveData<String>?
) {
    if (orderQty?.value != 0 && variantPrice?.value != "0") {
        view.text =
            "Tổng tiền: " + convertPriceStringToCurrencyString((variantPrice?.value!!.toInt() * orderQty?.value!!.toInt()).toString())
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.INVISIBLE
    }
}

@BindingAdapter("cartImage")
fun cartImage(view: ImageView, cartImage: Result<ProductImage>?) {
    cartImage?.let {
        when (cartImage) {
            is Result.Success -> {
                bindImage(view, cartImage.data.url)
            }
            is Result.Loading -> {
            }
            else -> {
            }
        }
    }
}


@BindingAdapter("productDescription")
fun setProductDescription(view: TextView, productDetail: Result<ProductDetail>?) {
    productDetail?.let {
        when(productDetail) {
            is Result.Success -> view.text = productDetail.data.description
            else -> {}
        }
    }
}