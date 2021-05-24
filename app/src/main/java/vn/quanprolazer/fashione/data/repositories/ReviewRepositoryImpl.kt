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
import vn.quanprolazer.fashione.data.network.models.NetworkOrderItemStatusType
import vn.quanprolazer.fashione.data.network.models.UpdateOrderStatusRequest
import vn.quanprolazer.fashione.data.network.models.toDomainModel
import vn.quanprolazer.fashione.data.network.services.retrofits.OrderService
import vn.quanprolazer.fashione.data.network.services.retrofits.ReviewService
import vn.quanprolazer.fashione.domain.models.*
import vn.quanprolazer.fashione.domain.repositories.OrderRepository
import vn.quanprolazer.fashione.domain.repositories.ReviewRepository
import vn.quanprolazer.fashione.domain.repositories.UserRepository

class ReviewRepositoryImpl @AssistedInject constructor(
    private val reviewServiceRetrofit: ReviewService,
    private val reviewServiceFirestore: vn.quanprolazer.fashione.data.network.services.firestores.ReviewService,
    private val userRepository: UserRepository,
    private val orderServiceRetrofit: OrderService,
    @Assisted private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ReviewRepository {

    override suspend fun getRatings(productId: String): Resource<List<Rating>> {
        return try {
            val networkRatings = withContext(dispatcher) {
                reviewServiceFirestore.getRatings(productId)
            }
            Resource.Success(networkRatings.map { it.toDomainModel() })
        } catch (e: Exception) {
            Resource.Error(e)
        }

    }

    override suspend fun addReview(review: Review, rating: Rating): Resource<Boolean> {

        val user = userRepository.getUser().value
            ?: return Resource.Error(Exception("Not login yet"))

        return try {
            withContext(dispatcher) {
                val reviewId = reviewServiceFirestore.addReview(
                    review = review.toNetworkModel().copy(userId = user.uid)
                )
                val ratingWithId = rating.toNetworkModel().copy(reviewId = reviewId)
                reviewServiceFirestore.addRating(rating = ratingWithId)
                orderServiceRetrofit.updateOrderStatus(
                    UpdateOrderStatusRequest(
                        status = NetworkOrderItemStatusType.COMPLETE,
                        orderItemId = review.orderItemId
                    )
                )
                Resource.Success(true)
            }
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }

    override suspend fun getReviews(
        productId: String,
        lastVisibleId: String?
    ): Resource<ReviewRetrofitResponse> {
        return try {
            Resource.Success(
                reviewServiceRetrofit.getReviews(productId, lastVisibleId).toDomainModel()
            )
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }
}