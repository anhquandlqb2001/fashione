/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.utilities

import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
            "Tổng tiền: " + convertPriceStringToCurrencyString((variantPrice.toFloat() * orderQty.toInt()).toString())
        visibility = View.VISIBLE
    } else {
        visibility = View.INVISIBLE
    }
}

@BindingAdapter("checkoutQty")
fun TextView.setCheckoutQty(qty: Number) {
    text = "x${qty}"
}

@BindingAdapter(value = ["receiver_name", "phone_number"])
fun TextView.setCheckoutQty(receiverName: String?, phoneNumber: String?) {
    text = "$receiverName - $phoneNumber"
}

@BindingAdapter(value = ["address", "subdistrict_or_village", "privince_or_city", "district_or_town"])
fun TextView.pickupAddress(address: String?, subdistrictOrVillage: String?, privinceOrCcity: String?, districtOrTown: String?) {
    text = "$address, $subdistrictOrVillage, $privinceOrCcity, $districtOrTown"
}

@BindingAdapter(value = ["productPrice", "shipPrice"])
fun TextView.totalPrice(productPrice: String?, shipPrice: String?) {
    if (productPrice == null || shipPrice == null) {
        text = "0"
        return
    }
    val price = productPrice.toFloat() + shipPrice.toFloat()
    return toPrice(price.toString())
}

@BindingAdapter("toPrice")
fun TextView.toPrice(price: String?) {
    price?.let {
        text = convertPriceStringToCurrencyString(price)
    }
}