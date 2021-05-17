/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vn.quanprolazer.fashione.data.network.services.firestores.PurchaseService
import vn.quanprolazer.fashione.domain.models.OrderStatus
import vn.quanprolazer.fashione.domain.models.Purchase
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.repositories.PurchaseRepository
import vn.quanprolazer.fashione.domain.repositories.UserRepository
import javax.inject.Inject

class PurchaseRepositoryImpl @Inject constructor(
    private val purchaseService: PurchaseService,
    private val userRepository: UserRepository,
) : PurchaseRepository {
    override suspend fun getPurchaseItems(orderStatus: OrderStatus): Resource<MutableList<Purchase>> {

        val user =
            userRepository.getUser().value
                ?: return Resource.Error(Exception("Not Authenticate"))

        val ordersResponse = withContext(Dispatchers.IO) {
            purchaseService.getOrders(user.uid, orderStatus)
        }

        return when (ordersResponse) {
            is Resource.Success -> {
                val purchaseItems = mutableListOf<Purchase>()
                ordersResponse.data.forEach { networkOrder ->
                    when (val orderItemsResponse = purchaseService.getOrderItems(networkOrder.id)) {
                        is Resource.Success -> {
                            val purchases = orderItemsResponse.data.map { networkOrderItem ->
                                Purchase(
                                    productId = networkOrderItem.productId,
                                    id = networkOrderItem.id,
                                    userId = networkOrder.userId,
                                    variantOptionId = networkOrderItem.variantOptionId,
                                    productName = networkOrderItem.productName,
                                    variantName = networkOrderItem.variantName,
                                    variantValue = networkOrderItem.variantValue,
                                    quantity = networkOrderItem.quantity,
                                    price = networkOrderItem.price,
                                    status = networkOrder.status,
                                    variantId = networkOrderItem.variantId,
                                    reviewStatus = networkOrderItem.reviewStatus
                                )
                            }
                            purchaseItems.addAll(purchases)
                        }
                        else -> {
                            Resource.Error((orderItemsResponse as Resource.Error).exception)
                        }
                    }
                }
                return Resource.Success(purchaseItems)
            }
            else -> Resource.Error((ordersResponse as Resource.Error).exception)
        }
    }
}