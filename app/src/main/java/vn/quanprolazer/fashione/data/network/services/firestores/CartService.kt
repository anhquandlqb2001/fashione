/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import kotlinx.coroutines.flow.Flow
import vn.quanprolazer.fashione.data.network.models.NetworkCartItem
import vn.quanprolazer.fashione.domain.models.AddToCartItem

interface CartService {
    suspend fun getCartItemCount(userId: String): Flow<Int>

    suspend fun addToCart(
        addToCartItem: AddToCartItem,
        userId: String,
        documentId: String? = null
    ): Boolean

    suspend fun updateCartItem(cartItemId: String, quantity: Int): Boolean

    suspend fun getCartItems(userId: String): List<NetworkCartItem>

    suspend fun removeCartItem(cartItemId: String): Boolean

    suspend fun removeCartItems(cartItemIds: List<String>): Boolean
}