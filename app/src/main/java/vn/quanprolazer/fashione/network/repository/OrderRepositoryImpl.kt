/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vn.quanprolazer.fashione.domain.model.CartItem
import vn.quanprolazer.fashione.domain.model.Result
import vn.quanprolazer.fashione.domain.repository.OrderRepository
import vn.quanprolazer.fashione.network.service.OrderService

class OrderRepositoryImpl(
    private val orderService: OrderService,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : OrderRepository {
    override suspend fun addToCart(cartItem: CartItem, userId: String): Result<Boolean> {
        val result = withContext(defaultDispatcher) {
            orderService.addToCart(cartItem, userId)
        }

        return when (result) {
            is Result.Success -> Result.Success(true)
            is Result.Error -> Result.Error(result.exception)
        }
    }
}