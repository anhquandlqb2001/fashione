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

@BindingAdapter("imageUrl")
fun ImageView.bindImage(imageUrl: String?) {
    imageUrl?.let {
        val imgUri = imageUrl.toUri().buildUpon().scheme("https").build()
        GlideApp.with(context).load(imgUri).apply(
            RequestOptions().placeholder(R.drawable.loading_anim).error(R.drawable.ic_broken)
        ).into(this)
    }
}

@BindingAdapter("orderQty")
fun TextView.setOrderQty(orderQty: LiveData<Number>?) {
    orderQty?.value?.let {
        text = orderQty.value?.toString()
    }
}

@BindingAdapter(value = ["variantQty", "variantPrice"], requireAll = false)
fun TextView.setTotalPrice(orderQty: LiveData<Number>?, variantPrice: LiveData<String>?
) {
    if (orderQty?.value != 0 && variantPrice?.value != "0") {
        text =
            "Tổng tiền: " + convertPriceStringToCurrencyString((variantPrice?.value!!.toInt() * orderQty?.value!!.toInt()).toString())
        visibility = View.VISIBLE
    } else {
        visibility = View.INVISIBLE
    }
}