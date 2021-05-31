/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.utilities.bindings

import android.net.Uri
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions
import vn.quanprolazer.fashione.GlideApp
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.domain.models.ProductImage
import vn.quanprolazer.fashione.domain.models.Resource

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

@BindingAdapter("cartImage")
fun ImageView.cartImage(cartImage: Resource<ProductImage>?) {
    cartImage?.let {
        when (cartImage) {
            is Resource.Success -> {
                loadImage(cartImage.data.url)
            }
            is Resource.Loading -> {
            }
            else -> {
            }
        }
    }
}