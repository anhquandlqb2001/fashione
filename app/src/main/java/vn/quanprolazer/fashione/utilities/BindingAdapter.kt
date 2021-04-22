/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.utilities

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions
import vn.quanprolazer.fashione.GlideApp
import vn.quanprolazer.fashione.R

@BindingAdapter("imageUrl")
fun ImageView.loadImage(imageUrl: String?) {
    imageUrl?.let {
        val imgUri = imageUrl.toUri().buildUpon().scheme("https").build()
        GlideApp.with(context).load(imgUri).apply(
            RequestOptions().placeholder(R.drawable.loading_anim).error(R.drawable.ic_broken)
        ).into(this)
    }
}

@BindingAdapter("orderQty")
fun TextView.setOrderQty(orderQty: Number?) {
    orderQty?.let {
        text = orderQty.toString()
    }
}

@BindingAdapter(value = ["variantQty", "variantPrice"], requireAll = false)
fun TextView.setTotalPrice(orderQty: Number?, variantPrice: String?) {
    if (orderQty == null || variantPrice == null) return
    if (orderQty != 0 && variantPrice != "0") {
        text =
            "Tổng tiền: " + convertPriceStringToCurrencyString((variantPrice.toInt() * orderQty.toInt()).toString())
        visibility = View.VISIBLE
    } else {
        visibility = View.INVISIBLE
    }
}