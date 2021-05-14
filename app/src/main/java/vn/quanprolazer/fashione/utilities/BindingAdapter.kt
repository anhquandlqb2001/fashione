/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.utilities

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions
import vn.quanprolazer.fashione.GlideApp
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.data.domain.model.*

@BindingAdapter("imageUrl")
fun ImageView.loadImage(imageUrl: String?) {
    imageUrl?.let {
        val imgUri = imageUrl.toUri().buildUpon().scheme("https").build()
        GlideApp.with(context).load(imgUri).apply(
            RequestOptions().placeholder(R.drawable.loading_anim).error(R.drawable.ic_broken)
        ).into(this)
    }
}

@BindingAdapter("imageUri")
fun ImageView.loadImage(imageUri: Uri?) {
    imageUri?.let {
        GlideApp.with(context).load(imageUri).apply(
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

@BindingAdapter("setQty")
fun TextView.setQty(qty: Number) {
    text = "x${qty}"
}

@BindingAdapter(value = ["receiver_name", "phone_number"])
fun TextView.setQty(receiverName: String?, phoneNumber: String?) {
    text = "$receiverName - $phoneNumber"
}

@BindingAdapter(value = ["address", "subdistrict_or_village", "privince_or_city", "district_or_town"])
fun TextView.pickupAddress(
    address: String?,
    subdistrictOrVillage: String?,
    provinceOrCity: String?,
    districtOrTown: String?
) {
    text = "$address, $subdistrictOrVillage, $provinceOrCity, $districtOrTown"
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

@BindingAdapter("imgResource")
fun ImageView.imgResource(img: Resource<ProductImage>?) {
    img?.let {
        when (img) {
            is Resource.Success -> loadImage(img.data.url)
            else -> {
            }
        }
    }
}

@BindingAdapter("purchaseItemStatus")
fun TextView.purchaseItemStatus(status: OrderStatus?) {
    status?.let {
        text = status.status
        when (status) {
            OrderStatus.CONFIRMING -> setTextColor(resources.getColor(R.color.teal_200, context.theme))
            OrderStatus.DELIVERING -> setTextColor(resources.getColor(R.color.teal_200, context.theme))
            OrderStatus.DELIVERED -> setTextColor(resources.getColor(R.color.teal_700, context.theme))
        }
    }
}

@BindingAdapter("purchaseReviewStatus")
fun LinearLayout.purchaseReviewStatus(status: ReviewStatus?) {
    status?.let {
        visibility = when (status) {
            ReviewStatus.YES -> View.GONE
            ReviewStatus.NO -> View.VISIBLE
        }
    }
}


@BindingAdapter("purchaseDelivered")
fun RelativeLayout.purchaseDelivered(status: OrderStatus?) {
    status?.let {
        visibility = if (status == OrderStatus.DELIVERED) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}

