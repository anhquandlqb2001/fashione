/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.repository

import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import vn.quanprolazer.fashione.data.domain.model.AddToCartItem
import vn.quanprolazer.fashione.data.domain.model.CartItem
import vn.quanprolazer.fashione.data.domain.model.ProductImage
import vn.quanprolazer.fashione.data.domain.model.Result
import vn.quanprolazer.fashione.data.domain.repository.OrderRepository
import vn.quanprolazer.fashione.data.domain.repository.ProductRepository
import vn.quanprolazer.fashione.data.domain.repository.UserRepository
import vn.quanprolazer.fashione.data.network.mapper.NetworkCartItemsMapper
import vn.quanprolazer.fashione.data.network.service.OrderService
import java.lang.Exception

class OrderRepositoryImpl @AssistedInject constructor(private val orderService: OrderService,
                                                      private val userRepository: UserRepository,
                                                      private val productRepository: ProductRepository,
                                                      @Assisted private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : OrderRepository {
    override suspend fun addToCart(addToCartItem: AddToCartItem): Result<Boolean> {
        val result = withContext(defaultDispatcher) {
            orderService.addToCart(addToCartItem, userRepository.getUser().value!!.uid)
        }

        return when (result) {
            is Result.Success -> Result.Success(true)
            is Result.Error -> Result.Error(result.exception)
            else -> Result.Loading(null)
        }
    }

    override suspend fun getCartItems(): Result<MutableList<CartItem>> {
        val result = withContext(defaultDispatcher) {
            orderService.getCartItems(userRepository.getUser().value!!.uid)
        }

        return when (result) {
            is Result.Success -> Result.Success(NetworkCartItemsMapper.map(result.data).toMutableList())
            is Result.Error -> Result.Error(result.exception)
            else -> Result.Loading(null)
        }
    }

    override suspend fun updateCartItem(cartItemId: String, quantity: Int): Result<Boolean> {
        val result = withContext(defaultDispatcher) {
            orderService.updateCartItem(cartItemId, quantity)
        }
        return when (result) {
            is Result.Success -> Result.Success(result.data)
            is Result.Error -> Result.Error(result.exception)
            else -> Result.Loading(null)
        }
    }
}
