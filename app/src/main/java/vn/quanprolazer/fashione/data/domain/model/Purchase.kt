/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.domain.model


data class Purchase(
    val id: String,
    val userId: String,
    val productId: String,
    val variantId: String,
    val variantOptionId: String,
    val productName: String,
    val variantName: String,
    val variantValue: String,
    var quantity: Int,
    val price: String,
    var purchaseImage: Resource<ProductImage>? = null,
    val status: OrderStatus
)