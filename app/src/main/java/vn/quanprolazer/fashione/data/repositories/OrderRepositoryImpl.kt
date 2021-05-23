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
import vn.quanprolazer.fashione.data.network.models.NetworkDeliveryStatus
import vn.quanprolazer.fashione.data.network.models.toDomainModel
import vn.quanprolazer.fashione.data.network.services.firestores.OrderService
import vn.quanprolazer.fashione.domain.models.*
import vn.quanprolazer.fashione.domain.repositories.OrderRepository
import vn.quanprolazer.fashione.domain.repositories.UserRepository


class OrderRepositoryImpl @AssistedInject constructor(
    private val orderService: OrderService,
    private val userRepository: UserRepository,
    private val orderRetrofitService: vn.quanprolazer.fashione.data.network.services.retrofits.OrderService,
    @Assisted private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : OrderRepository {

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

    override suspend fun updateOrderReviewStatus(
        status: ReviewStatus,
        orderItemId: String
    ): Resource<Boolean> {
        val updateOrderReviewStatusResponse = withContext(defaultDispatcher) {
            orderService.updateOrderReviewStatus(reviewStatus = status, orderItemId)
        }
        return when (updateOrderReviewStatusResponse) {
            is Resource.Success -> Resource.Success(true)
            else -> Resource.Error(Exception("Error when update review status"))
        }
    }

    override suspend fun getDeliveryStatus(): Resource<List<DeliveryStatus>> {
        userRepository.getUser().value
            ?: return Resource.Error(Exception("Not login yet"))

        val token = try {
            withContext(defaultDispatcher) {
                userRepository.getToken()
            }
        } catch (e: Exception) {
            Timber.e(e)
        } ?: return Resource.Error(Exception("Token not found"))


        return try {
            val deliveryStatusResponse = withContext(defaultDispatcher) {
                orderRetrofitService.getDeliveryStatus(token as String)
            }
            Resource.Success(deliveryStatusResponse.map { it.toDomainModel() })
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }

    }
}
