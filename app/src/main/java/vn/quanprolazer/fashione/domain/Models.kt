/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */
package vn.quanprolazer.fashione.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * Domain objects are plain Kotlin data classes that represent the things in our app. These are the
 * objects that should be displayed on screen, or manipulated by the app.
 *
 * @see network for objects that parse or prepare network calls
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
    val price: String
) : Parcelable


/**
 * Contain data for image class in app
 */
data class ProductImage(
    val productImageId: String,
    val productImageUrl: String)


/**
 * Category class represent a category in app
 */
@Parcelize
data class Category(
    val id: String,
    val name: String
) : Parcelable