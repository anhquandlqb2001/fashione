/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.model

data class CartItem(
    val productId: String,
    val userId: String,
    var variantId: String = "",
    var variantOptionId: String = "",
    var variantName: String = "",
    var variantValue: String = "",
    var quantity: Int = 0,
    var price: String = "0"
)
