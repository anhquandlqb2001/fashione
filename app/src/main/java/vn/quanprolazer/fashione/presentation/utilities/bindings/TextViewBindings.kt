/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.utilities.bindings

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.google.firebase.Timestamp
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.domain.models.*
import vn.quanprolazer.fashione.presentation.utilities.PRODUCT_NAME_LIMIT
import vn.quanprolazer.fashione.presentation.utilities.convertPriceStringToCurrencyString
import vn.quanprolazer.fashione.presentation.utilities.fromTimestamp
import vn.quanprolazer.fashione.presentation.utilities.smartTruncate
import java.util.*


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
            convertPriceStringToCurrencyString((variantPrice.toFloat() * orderQty.toInt()).toString())
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


@BindingAdapter("purchaseItemStatus")
fun TextView.purchaseItemStatus(status: OrderItemStatusType?) {
    status?.let {
        text = status.status
        when (status) {
            OrderItemStatusType.CONFIRMING -> setTextColor(
                resources.getColor(
                    R.color.teal_200,
                    context.theme
                )
            )
            OrderItemStatusType.DELIVERING -> setTextColor(
                resources.getColor(
                    R.color.teal_200,
                    context.theme
                )
            )
            OrderItemStatusType.DELIVERED -> setTextColor(
                resources.getColor(
                    R.color.teal_700,
                    context.theme
                )
            )
        }
    }
}


@BindingAdapter("rate")
fun TextView.rate(rate: Int?) {
    rate?.let {
        text = "${rate}/5"
    }
}

@BindingAdapter(value = ["createdAt", "variantName", "variantValue"])
fun TextView.reviewProduct(createdAt: String?, variantName: String?, variantValue: String?) {
    if (createdAt == null || variantName == null || variantValue == null) text = ""
    text = "${fromTimestamp(Timestamp(Date(createdAt)))} | ${variantName} - ${variantValue}"
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
        when (product) {
            is Resource.Success -> {
                setProductName(product.data.name)
            }
            else -> {
            }
        }
    }
}

@BindingAdapter("cartItemQuantity")
fun TextView.cartItemQuantity(cartItem: CartItem?) {
    cartItem?.let {
        text = cartItem.quantity.toString()
    }
}


@BindingAdapter("totalPrice")
fun TextView.calculateTotalPrice(orderData: LiveData<OrderData>?) {
    orderData?.value?.let {
        val priceText = orderData.value!!.totalPrice
        val priceCurrency = convertPriceStringToCurrencyString(priceText)

        text = resources.getString(R.string.total_price_text, priceCurrency)
        return
    }

    text = resources.getString(R.string.total_price_text, convertPriceStringToCurrencyString("0"))
}


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

@BindingAdapter("timestamp")
fun TextView.timestamp(timestamp: Timestamp?) {
    timestamp?.let {
        text = fromTimestamp(timestamp)
    }
}

@BindingAdapter("timestampToDate")
fun TextView.timestampToDate(timestamp: Timestamp?) {
    timestamp?.let {
        text = fromTimestamp(timestamp, pattern = "dd/MM/yyyy")
    }
}

@BindingAdapter("timestampToHour")
fun TextView.timestampToHour(timestamp: Timestamp?) {
    timestamp?.let {
        text = fromTimestamp(timestamp, pattern = "HH:mm")
    }
}
