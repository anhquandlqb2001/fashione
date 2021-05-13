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
import vn.quanprolazer.fashione.data.domain.mapper.CartItemMapper
import vn.quanprolazer.fashione.data.domain.model.*
import vn.quanprolazer.fashione.data.domain.repository.OrderRepository
import vn.quanprolazer.fashione.data.domain.repository.UserRepository
import vn.quanprolazer.fashione.data.network.mapper.NetworkCartItemsMapper
import vn.quanprolazer.fashione.data.network.service.OrderService

class OrderRepositoryImpl @AssistedInject constructor(
    private val orderService: OrderService,
    private val userRepository: UserRepository,
    @Assisted private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : OrderRepository {
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
                NetworkCartItemsMapper.map(result.data).toMutableList()
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
                CartItemMapper.map(cartItem), userRepository.getUser().value!!.uid, cartItem.id
            )
        }

        return fromResult(result)
    }

    override suspend fun createOrder(order: Order, orderItems: List<OrderItem>): Resource<Boolean> {
        val createOrderResult = withContext(defaultDispatcher) {
            orderService.createOrder(order)
        }

        return when (createOrderResult) {
            is Resource.Success -> {
                val updatedOrderItems = orderItems.map {
                    it.copy(orderId = createOrderResult.data)
                }
                val addOrderItemsResult = withContext(defaultDispatcher) {
                    orderService.createOrderItem(updatedOrderItems)
                }
                return when (addOrderItemsResult) {
                    is Resource.Success -> {
                        withContext(defaultDispatcher) {
                            orderService.removeCartItems(orderItems.map { it.cartItemId!! })
                        }
                        return Resource.Success(true)
                    }
                    is Resource.Error -> Resource.Error(addOrderItemsResult.exception)
                    is Resource.Loading -> Resource.Loading(null)
                }
            }
            is Resource.Loading -> Resource.Loading(null)
            is Resource.Error -> Resource.Error(createOrderResult.exception)
        }

    }
}
