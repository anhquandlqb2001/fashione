/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.model

data class CartItem(
    val id: String,
    val productId: String,
    val userId: String,
    val variantId: String,
    val variantOptionId: String,
    val variantName: String,
    val variantValue: String,
    var quantity: Int,
    val price: String,
    var cartItemImg: Resource<ProductImage>?,
    var product: Resource<Product>? = null
)
