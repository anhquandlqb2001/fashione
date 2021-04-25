/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.model

data class OrderData(
    var totalPrice: String = "0",
    var items: MutableList<CartItem> = mutableListOf()
)