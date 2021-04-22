/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Domain objects are plain Kotlin data classes that represent the things in our app. These are the
 * objects that should be displayed on screen, or manipulated by the app.
 *
 * Domain Model is free from frameworks
 *
 */

/**
 * Product class represent a product in app
 */
@Parcelize
data class Product(val id: String, //Document ID is actually the user id
                   val categoryId: String,
                   val name: String,
                   val thumbnailUrl: String,
                   val price: String
) : Parcelable


@Parcelize
data class ProductDetail(
    val id: String = "",
    val productId: String = "",
    val description: String = "",
) : Parcelable


@Parcelize
data class ProductImage(
    val id: String,
    val productId: String,
    val variantId: String,
    val variantOptionId: String,
    val url: String,
) : Parcelable

data class ProductVariant(val id: String = "",
                          val name: String = "",
                          var options: Result<List<ProductVariantOption>>? = null
)


@Parcelize
data class ProductVariantOption(val id: String,
                                val value: String,
                                val quantity: Number,
                                val price: String
) : Parcelable


