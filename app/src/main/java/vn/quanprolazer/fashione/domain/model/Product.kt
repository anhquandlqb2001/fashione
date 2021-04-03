/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.model



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
data class Product(
    val id: String, //Document ID is actually the user id
    val categoryId: String,
    val name: String,
    val thumbnailUrl: String,
    val price: String,
    val detail: ProductDetail
) {
}


interface ProductDetail {
    val id: String
    val productId: String
    val quantity: Number
    val description: String
    val images: List<ProductImage>
    val variants: List<ProductVariant>
}

interface ProductImage {
    val id: String
    val url: String
}

interface ProductVariant {
    val name: String
    val size: String
    val qty: Number
}

object EmptyProductImage : ProductImage {
    override val id: String = ""
    override val url: String = ""
}

object EmptyProductVariant : ProductVariant {
    override val name: String = ""
    override val size: String = ""
    override val qty: Number = -1
}

object EmptyProductDetail : ProductDetail {
    override val id: String = "-1"
    override val productId: String = "-1"
    override val quantity: Number = 1
    override val description: String = ""
    override val images: List<ProductImage> = listOf()
    override val variants: List<ProductVariant> = listOf()
}

