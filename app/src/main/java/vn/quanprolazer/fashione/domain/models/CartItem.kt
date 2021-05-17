/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.models

data class CartItem(
    val id: String,
    val productId: String,
    val userId: String,
    val variantId: String,
    val variantOptionId: String,
    val variantName: String,
    val variantValue: String,
    var quantity: Int,
    var price: String,
    var cartItemImg: Resource<ProductImage>?,
    var product: Resource<Product>? = null,
    var isChecked: Boolean = false
)

internal fun CartItem.toNetworkModel() = AddToCartItem(
    productId,
    userId,
    variantId,
    variantOptionId,
    variantName,
    variantValue,
    quantity,
    price
)

internal fun CartItem.toCheckoutItem() = CheckoutItem(
    id,
    productId,
    userId,
    variantId,
    variantOptionId,
    (product as Resource.Success).data.name,
    variantValue,
    variantName,
    quantity,
    price,
    (cartItemImg as Resource.Success).data.url
)