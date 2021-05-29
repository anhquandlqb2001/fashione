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
import timber.log.Timber
import vn.quanprolazer.fashione.data.network.models.toDomainModel
import vn.quanprolazer.fashione.data.network.services.firestores.CartService
import vn.quanprolazer.fashione.domain.models.AddToCartItem
import vn.quanprolazer.fashione.domain.models.CartItem
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.models.toNetworkModel
import vn.quanprolazer.fashione.domain.repositories.CartRepository
import vn.quanprolazer.fashione.domain.repositories.UserRepository

class CartRepositoryImpl @AssistedInject constructor(
    private val userRepository: UserRepository,
    private val cartService: CartService,
    @Assisted private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) :
    CartRepository {

    override suspend fun addToCart(addToCartItem: AddToCartItem): Resource<Boolean> {
        return try {
            withContext(defaultDispatcher) {
                cartService.addToCart(addToCartItem, userRepository.getUser().value!!.uid)
            }
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getCartItems(): Resource<MutableList<CartItem>> {
        val result = withContext(defaultDispatcher) {
            cartService.getCartItems(userRepository.getUser().value!!.uid)
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
        return try {
            withContext(defaultDispatcher) {
                cartService.updateCartItem(cartItemId, quantity)
            }
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun removeCartItem(cartItemId: String): Resource<Boolean> {
        return try {
            withContext(defaultDispatcher) {
                cartService.removeCartItem(cartItemId)
            }
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun undoDeleteCartItem(cartItem: CartItem): Resource<Boolean> {
        return try {
            withContext(defaultDispatcher) {
                cartService.addToCart(
                    cartItem.toNetworkModel(), userRepository.getUser().value!!.uid, cartItem.id
                )
            }
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getCartItemCount(): Resource<Int> {
        val user =
            userRepository.getUser().value ?: return Resource.Error(Exception("Not login yet"))
        return try {
            val response = withContext(defaultDispatcher) {
                cartService.getCartItemCount(user.uid)
            }
            Resource.Success(response)
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }
}