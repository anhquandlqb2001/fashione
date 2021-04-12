/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.model

data class ProductOrder(
    val productId: String,
    var variantName: String = "",
    var variantValue: String = "",
    var qty: Number = 0
)