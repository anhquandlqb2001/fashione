/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.model

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
data class Product(
    val id: String, //Document ID is actually the user id
    val categoryId: String,
    val name: String,
    val thumbnailUrl: String,
    val price: String,
    var detail: ProductDetail,
    val variants: List<ProductVariant>
) : Parcelable


@Parcelize
data class ProductDetail(
    val id: String = "",
    val productId: String = "",
    val description: String = "",
    val images: List<ProductImage> = listOf(),
) : Parcelable


@Parcelize
data class ProductImage(
    val url: String,
) : Parcelable

@Parcelize
data class ProductVariant(
    val id: String = "",
    val name: String = "",
    var options: List<ProductVariantOption> = listOf()
) : Parcelable


@Parcelize
data class ProductVariantOption(
    val id: String,
    val value: String,
    val quantity: Number,
    val price: String
) : Parcelable


data class CartItem(
    val productId: String,
    var variantId: String = "",
    var variantOptionId: String = "",
    var variantName: String = "",
    var variantValue: String = "",
    var qty: Number = 0,
    var price: String = "0"
)
