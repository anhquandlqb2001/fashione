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
    val detail: ProductDetail
) : Parcelable


@Parcelize
data class ProductDetail(
    val id: String = "",
    val productId: String = "",
    val qty: Int = -1,
    val description: String = "",
    val images: List<ProductImage> = listOf(),
    val variants: List<ProductVariant> = listOf(),
) : Parcelable


@Parcelize
data class ProductImage(
    val url: String,
) : Parcelable

@Parcelize
data class ProductVariant(
    val name: String,
    val options: List<ProductVariantOption>
) : Parcelable


@Parcelize
data class ProductVariantOption(
    val value: String,
    val qty: Number,
    val price: String
) : Parcelable

data class CartItem(
    val productId: String,
    var variantName: String = "",
    var variantValue: String = "",
    var qty: Number = 0,
    val price: String = "0"
)

//
//object EmptyProductImage : ProductImage {
//    override val id: String = ""
//    override val url: String = ""
//}
//
//object EmptyProductVariant : ProductVariant {
//    override val name: String = ""
//    override val size: String = ""
//    override val qty: Number = -1
//}
//
//object EmptyProductDetail : ProductDetail() {
//    override val id: String = "-1"
//    override val productId: String = "-1"
//    override val quantity: Number = 1
//    override val description: String = ""
//    override val images: List<ProductImage> = listOf()
//    override val variants: List<ProductVariant> = listOf()
//}
//
