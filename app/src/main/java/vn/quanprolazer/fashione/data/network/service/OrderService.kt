/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.service

import com.google.firebase.firestore.DocumentId
import vn.quanprolazer.fashione.data.domain.model.AddToCartItem
import vn.quanprolazer.fashione.data.domain.model.Resource
import vn.quanprolazer.fashione.data.network.dto.NetworkCartItem

interface OrderService {

    suspend fun addToCart(addToCartItem: AddToCartItem, userId: String, documentId: String? = null): Resource<Boolean>

    suspend fun updateCartItem(cartItemId: String, quantity: Int): Resource<Boolean>

    suspend fun getCartItems(userId: String): Resource<List<NetworkCartItem>>

    suspend fun removeCartItem(cartItemId: String): Resource<Boolean>

}