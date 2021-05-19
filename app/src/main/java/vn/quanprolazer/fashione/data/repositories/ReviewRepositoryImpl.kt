/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.repositories

import com.google.firebase.firestore.DocumentSnapshot
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import vn.quanprolazer.fashione.data.network.models.toDomainModel
import vn.quanprolazer.fashione.data.network.services.retrofits.ReviewService
import vn.quanprolazer.fashione.domain.models.*
import vn.quanprolazer.fashione.domain.repositories.OrderRepository
import vn.quanprolazer.fashione.domain.repositories.ReviewRepository
import vn.quanprolazer.fashione.domain.repositories.UserRepository

class ReviewRepositoryImpl @AssistedInject constructor(
    private val reviewServiceRetrofit: ReviewService,
    private val reviewServiceFirestore: vn.quanprolazer.fashione.data.network.services.firestores.ReviewService,
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository,
    @Assisted private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ReviewRepository {

    override suspend fun getRatings(productId: String): Resource<List<Rating>> {
        val getRatingsResponse = withContext(dispatcher) {
            reviewServiceFirestore.getRatings(productId)
        }
        return when (getRatingsResponse) {
            is Resource.Success -> {
                if (getRatingsResponse.data.isEmpty()) return Resource.Success(listOf())
                Resource.Success(getRatingsResponse.data.map { it.toDomainModel() })
            }
            else -> Resource.Error((getRatingsResponse as Resource.Error).exception)
        }
    }

    override suspend fun addReview(review: Review, rating: Rating): Resource<Boolean> {

        val user = userRepository.getUser().value
            ?: return Resource.Error(Exception("Not login yet"))

        val addReviewResponse = withContext(dispatcher) {
            reviewServiceFirestore.addReview(review = review.toNetworkModel().copy(userId = user.uid))
        }
        return when (addReviewResponse) {
            is Resource.Success -> {
                // if success? add rating
                val ratingWithId = rating.toNetworkModel().copy(reviewId = addReviewResponse.data)
                val addReviewRatingResponse = withContext(dispatcher) {
                    reviewServiceFirestore.addRating(rating = ratingWithId)
                }

                return when (addReviewRatingResponse) {
                    is Resource.Success -> {
                        val updateOrderReviewStatusResponse = withContext(dispatcher) {
                            orderRepository.updateOrderReviewStatus(
                                ReviewStatus.YES,
                                review.orderItemId
                            )
                        }
                        return when (updateOrderReviewStatusResponse) {
                            is Resource.Success -> Resource.Success(true)
                            else -> Resource.Error((updateOrderReviewStatusResponse as Resource.Error).exception)
                        }
                    }
                    else -> Resource.Error(Exception("Error on adding product review rating"))
                }
            }
            else -> Resource.Error(Exception("Error on adding product review"))
        }
    }

    override suspend fun getReviews(
        productId: String,
        lastVisibleId: String?
    ): Resource<ReviewRetrofitResponse> {
        return try {
            Resource.Success(reviewServiceRetrofit.getReviews(productId, lastVisibleId).toDomainModel())
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }
}