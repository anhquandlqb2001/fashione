/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */


package vn.quanprolazer.fashione.data.network.services.firestores

import vn.quanprolazer.fashione.data.network.models.NetworkCartItem
import vn.quanprolazer.fashione.domain.models.AddToCartItem
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.models.ReviewStatus

interface OrderService {

    suspend fun addToCart(
        addToCartItem: AddToCartItem,
        userId: String,
        documentId: String? = null
    ): Resource<Boolean>

    suspend fun updateCartItem(cartItemId: String, quantity: Int): Resource<Boolean>

    suspend fun getCartItems(userId: String): Resource<List<NetworkCartItem>>

    suspend fun removeCartItem(cartItemId: String): Resource<Boolean>

    suspend fun removeCartItems(cartItemIds: List<String>): Resource<Boolean>

    suspend fun updateOrderReviewStatus(
        reviewStatus: ReviewStatus,
        orderItemId: String
    ): Resource<Boolean>

}