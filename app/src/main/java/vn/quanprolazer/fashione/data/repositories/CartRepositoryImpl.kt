/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.repositories

import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vn.quanprolazer.fashione.data.network.models.toDomainModel
import vn.quanprolazer.fashione.data.network.services.firestores.OrderService
import vn.quanprolazer.fashione.domain.models.*
import vn.quanprolazer.fashione.domain.repositories.CartRepository
import vn.quanprolazer.fashione.domain.repositories.UserRepository

class CartRepositoryImpl @AssistedInject constructor(
    private val userRepository: UserRepository,
    private val orderService: OrderService,
    @Assisted private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) :
    CartRepository {

    override suspend fun addToCart(addToCartItem: AddToCartItem): Resource<Boolean> {
        val result = withContext(defaultDispatcher) {
            orderService.addToCart(addToCartItem, userRepository.getUser().value!!.uid)
        }

        return when (result) {
            is Resource.Success -> Resource.Success(true)
            is Resource.Error -> Resource.Error(result.exception)
            else -> Resource.Loading(null)

        }
    }

    override suspend fun getCartItems(): Resource<MutableList<CartItem>> {
        val result = withContext(defaultDispatcher) {
            orderService.getCartItems(userRepository.getUser().value!!.uid)
        }

        return when (result) {
            is Resource.Success -> Resource.Success(
                result.data.map { it.toDomainModel() }.toMutableList()
            )
            is Resource.Error -> Resource.Error(result.exception)
            else -> Resource.Loading(null)
        }
    }


    override suspend fun updateCartItem(cartItemId: String, quantity: Int): Resource<Boolean> {
        val result = withContext(defaultDispatcher) {
            orderService.updateCartItem(cartItemId, quantity)
        }
        return when (result) {
            is Resource.Success -> Resource.Success(result.data)
            is Resource.Error -> Resource.Error(result.exception)
            else -> Resource.Loading(null)
        }
    }

    override suspend fun removeCartItem(cartItemId: String): Resource<Boolean> {
        val result = withContext(defaultDispatcher) {
            orderService.removeCartItem(cartItemId)
        }

        return fromResult(result)
    }

    override suspend fun undoDeleteCartItem(cartItem: CartItem): Resource<Boolean> {
        val result = withContext(defaultDispatcher) {
            orderService.addToCart(
                cartItem.toNetworkModel(), userRepository.getUser().value!!.uid, cartItem.id
            )
        }

        return fromResult(result)
    }

}