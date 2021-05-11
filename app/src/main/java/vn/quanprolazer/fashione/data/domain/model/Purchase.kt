/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.model



data class Purchase(
    val userId: String,
    val variantOptionId: String,
    val productName: String,
    val variantName: String,
    val variantValue: String,
    var quantity: Int,
    val price: String,
    var cartItemImg: String? = null,
    val status: OrderStatus
)