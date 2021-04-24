/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.model

data class Order(
    val totalPrice: String,
    val items: MutableList<CartItem>
)
