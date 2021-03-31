package vn.quanprolazer.fashione.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val productId: String, //Document ID is actually the user id
    val categoryId: String,
    val productName: String,
    val productImageUrl: String,
    val productPrice: String
) : Parcelable


data class ProductImage(
    val productImageId: String,
    val productImageUrl: String)

@Parcelize
data class Category(
    val categoryId: String,
    val categoryName: String
) : Parcelable