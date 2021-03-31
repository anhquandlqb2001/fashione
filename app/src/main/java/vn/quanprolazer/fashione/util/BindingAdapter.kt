package vn.quanprolazer.fashione.util

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.domain.Product
import vn.quanprolazer.fashione.domain.ProductImage
import vn.quanprolazer.fashione.ui.product.ProductImageAdapter
import java.text.NumberFormat
import java.util.*

private const val PRODUCT_NAME_LIMIT = 10

@BindingAdapter("productPrice")
fun setProductPrice(view: TextView, productPrice: String?) {
    productPrice?.let {
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("VND")
        view.text = format.format(productPrice.toInt())
    }
}

@BindingAdapter("productName")
fun setProductName(view: TextView, productName: String?) {
    productName?.let {
        view.text = productName.smartTruncate(PRODUCT_NAME_LIMIT)
    }
}

@BindingAdapter("listData")
fun bindProductImageRecyclerView(recyclerView: RecyclerView,
                     data: Product?) {

    data?.let {
        val adapter = recyclerView.adapter as ProductImageAdapter
//        adapter.submitList()
    }
}

@BindingAdapter("imageUrl")
fun bindImage(view: ImageView, imageUrl: String?) {
    imageUrl?.let {
        val imgUri =
            imageUrl
                .toUri()
                .buildUpon()
                .scheme("https")
                .build()
        Glide.with(view.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_anim)
                    .error(R.drawable.ic_broken)
            )
            .into(view)
    }
}



