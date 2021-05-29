/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import vn.quanprolazer.fashione.data.network.models.NetworkCartItem
import vn.quanprolazer.fashione.domain.models.AddToCartItem
import vn.quanprolazer.fashione.domain.models.Resource

interface CartService {
    suspend fun getCartItemCount(userId: String): Int

    suspend fun addToCart(
        addToCartItem: AddToCartItem,
        userId: String,
        documentId: String? = null
    ): Boolean

    suspend fun updateCartItem(cartItemId: String, quantity: Int): Boolean

    suspend fun getCartItems(userId: String): Resource<List<NetworkCartItem>>

    suspend fun removeCartItem(cartItemId: String): Boolean

    suspend fun removeCartItems(cartItemIds: List<String>): Boolean
}