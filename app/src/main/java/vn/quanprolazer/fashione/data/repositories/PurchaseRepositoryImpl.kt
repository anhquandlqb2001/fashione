/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vn.quanprolazer.fashione.data.network.services.firestores.PurchaseService
import vn.quanprolazer.fashione.data.network.services.firestores.ReviewService
import vn.quanprolazer.fashione.domain.models.OrderStatus
import vn.quanprolazer.fashione.domain.models.Purchase
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.repositories.PurchaseRepository
import vn.quanprolazer.fashione.domain.repositories.UserRepository
import javax.inject.Inject

class PurchaseRepositoryImpl @Inject constructor(
    private val purchaseService: PurchaseService,
    private val userRepository: UserRepository,
    private val reviewService: ReviewService
) : PurchaseRepository {
    override suspend fun getPurchaseItems(orderStatus: OrderStatus): Resource<MutableList<Purchase>> {

        val user =
            userRepository.getUser().value
                ?: return Resource.Error(Exception("Not Authenticate"))

        return try {
            return withContext(Dispatchers.Default) {
                val orderStatuses = purchaseService.getOrderStatus(userId = user.uid, orderStatus)

                /**
                 * if not found any order_status match query, then return empty list
                 */
                if (orderStatuses.isNullOrEmpty()) return@withContext Resource.Success(mutableListOf())


                val orderItems = purchaseService.getOrderItems(orderStatuses.map { it.orderItemId })

                val purchaseItems = orderItems.map {
//                    val reviewStatus = if (orderStatus != OrderStatus.DELIVERED) {
//                        ReviewStatus.NO
//                    } else {
//                        reviewService.checkUserWithThisItemHasReview(it.id).toDomainModel()
//                    }
                    Purchase(
                        productId = it.productId,
                        id = it.id,
                        userId = it.userId,
                        variantOptionId = it.variantOptionId,
                        productName = it.productName,
                        variantName = it.variantName,
                        variantValue = it.variantValue,
                        quantity = it.quantity,
                        price = it.price,
                        status = OrderStatus.DELIVERING,
                        variantId = it.variantId
                    )
                }
                Resource.Success(purchaseItems.toMutableList())
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}