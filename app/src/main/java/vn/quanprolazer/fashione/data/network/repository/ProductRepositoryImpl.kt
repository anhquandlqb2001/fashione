/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Source
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vn.quanprolazer.fashione.data.domain.model.*
import vn.quanprolazer.fashione.data.domain.repository.OrderRepository
import vn.quanprolazer.fashione.data.domain.repository.ProductRepository
import vn.quanprolazer.fashione.data.network.dto.NetworkReview
import vn.quanprolazer.fashione.data.network.dto.toDomainModel
import vn.quanprolazer.fashione.data.network.mapper.*
import vn.quanprolazer.fashione.data.network.service.ProductService
import vn.quanprolazer.fashione.data.network.service.SearchServiceImpl

class ProductRepositoryImpl @AssistedInject constructor(
    private val productService: ProductService,
    private val orderRepository: OrderRepository,
    @Assisted private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ProductRepository {

    override suspend fun getProducts(source: Source): Resource<List<Product>> {

        val response = withContext(dispatcher) {
            productService.getProducts(source)
        }

        return when (response) {
            is Resource.Success -> Resource.Success(NetworkProductListMapper.map(response.data))
            is Resource.Loading -> Resource.Loading(null)
            is Resource.Error -> Resource.Error(response.exception)
        }
    }

    override suspend fun getProductsByCategoryId(categoryId: String): Resource<List<Product>> {
        val response = withContext(dispatcher) {
            productService.getProductsByCategoryId(categoryId)
        }

        return when (response) {
            is Resource.Success -> Resource.Success(NetworkProductListMapper.map(response.data))
            is Resource.Loading -> Resource.Loading(null)
            is Resource.Error -> Resource.Error(response.exception)
        }
    }

    override suspend fun findProductsByQuery(query: String): Resource<List<Product>> {
        val response = withContext(dispatcher) {
            SearchServiceImpl.findProductsByQuery(query)
        }

        return when (response) {
            is Resource.Success -> Resource.Success(NetworkProductListAlgoliaMapper.map(response.data))
            is Resource.Loading -> Resource.Loading(null)
            is Resource.Error -> Resource.Error(response.exception)
        }
    }

    override suspend fun getProductDetailByProductId(productId: String): Resource<ProductDetail> {
        val response = withContext(dispatcher) {
            productService.getProductDetailByProductId(productId)
        }

        return when (response) {
            is Resource.Success -> Resource.Success(NetworkProductDetailMapper.map(response.data))
            is Resource.Loading -> Resource.Loading(null)
            is Resource.Error -> Resource.Error(response.exception)
        }

    }

    override suspend fun getProductVariantsByProductId(productId: String): Resource<MutableList<ProductVariant>> {
        val response = withContext(dispatcher) {
            productService.getProductVariantsByProductId(productId)
        }

        return when (response) {
            is Resource.Success -> Resource.Success(
                NetworkProductVariantsMapper.map(response.data).toMutableList()
            )
            is Resource.Loading -> Resource.Loading(null)
            is Resource.Error -> Resource.Error(response.exception)
        }
    }

    override suspend fun getProductVariantOptionsByVariantId(variantId: String): Resource<List<ProductVariantOption>> {
        val response = withContext(dispatcher) {
            productService.getProductVariantOptionsByVariantId(variantId)
        }
        return when (response) {
            is Resource.Success -> Resource.Success(NetworkProductVariantOptionsMapper.map(response.data))
            is Resource.Error -> Resource.Error(response.exception)
            else -> Resource.Loading(null)
        }
    }

    override suspend fun getProductImagesByProductId(productId: String): Resource<List<ProductImage>> {
        val response = withContext(dispatcher) {
            productService.getProductImagesByProductId(productId)
        }

        return when (response) {
            is Resource.Success -> Resource.Success(NetworkProductImagesMapper.map(response.data))
            is Resource.Loading -> Resource.Loading(null)
            is Resource.Error -> Resource.Error(response.exception)
        }
    }

    override suspend fun getProductImageByProductId(productId: String): Resource<ProductImage> {
        val response = withContext(dispatcher) {
            productService.getProductImageByProductId(productId)
        }

        return when (response) {
            is Resource.Success -> Resource.Success(NetworkProductImageMapper.map(response.data))
            is Resource.Loading -> Resource.Loading(null)
            is Resource.Error -> Resource.Error(response.exception)
        }
    }

    override suspend fun getProductImageByProductVariantId(variantId: String): Resource<ProductImage> {
        val result = withContext(dispatcher) {
            (productService.getProductImageByVariantId(variantId))
        }

        return when (result) {
            is Resource.Success -> Resource.Success(NetworkProductImageMapper.map(result.data))
            is Resource.Error -> Resource.Error(result.exception)
            else -> Resource.Loading(null)
        }
    }

    override suspend fun getProductByProductId(productId: String): Resource<Product> {
        val result = withContext(dispatcher) {
            (productService.getProductByProductId(productId))
        }

        return when (result) {
            is Resource.Success -> Resource.Success(NetworkProductMapper.map(result.data))
            is Resource.Error -> Resource.Error(result.exception)
            else -> Resource.Loading(null)
        }
    }

    override suspend fun addReview(review: Review, rating: Rating): Resource<Boolean> {
        val addReviewResponse = withContext(dispatcher) {
            productService.addReview(review = review.toNetworkModel())
        }
        return when (addReviewResponse) {
            is Resource.Success -> {
                // if success? add rating
                val ratingWithId = rating.toNetworkModel().copy(reviewId = addReviewResponse.data)
                val addReviewRatingResponse = withContext(dispatcher) {
                    productService.addRating(rating = ratingWithId)
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

    override suspend fun getProductVariantOption(variantOptionId: String): Resource<ProductVariantOption> {
        val getProductVariantOptionResponse = withContext(dispatcher) {
            productService.getProductVariantOption(variantOptionId)
        }
        return when (getProductVariantOptionResponse) {
            is Resource.Success -> Resource.Success(getProductVariantOptionResponse.data.toDomainModel())
            else -> Resource.Error((getProductVariantOptionResponse as Resource.Error).exception)
        }
    }

    override suspend fun getReviewWithRating(
        productId: String,
        lastVisible: DocumentSnapshot?
    ): ReviewWithRatingResponse {
        val reviewsResponse = withContext(dispatcher) {
            productService.getReviews(productId, lastVisible)
        }

        return when (reviewsResponse) {
            is Resource.Success -> {
                val reviewWithRatings = mutableListOf<ReviewWithRating>()
                reviewsResponse.data.reviews.forEach {
                    val ratingsResponse = withContext(dispatcher) {
                        productService.getRating(it.id)
                    }
                    when (ratingsResponse) {
                        is Resource.Success -> reviewWithRatings.add(
                            ReviewWithRating(
                                it.toDomainModel(),
                                ratingsResponse.data.toDomainModel()
                            )
                        )
                        else -> {
                        }
                    }
                }
                ReviewWithRatingResponse(
                    Resource.Success(reviewWithRatings),
                    reviewsResponse.data.lastVisible
                )
            }
            else -> ReviewWithRatingResponse(
                Resource.Error(Exception("Error when collect review data")),
                null
            )
        }
    }

    override suspend fun getRatings(productId: String): Resource<List<Rating>> {
        val getRatingsResponse = withContext(dispatcher) {
            productService.getRatings(productId)
        }
        return when (getRatingsResponse) {
            is Resource.Success -> Resource.Success(getRatingsResponse.data.map { it.toDomainModel() })
            else -> Resource.Error((getRatingsResponse as Resource.Error).exception)
        }
    }

    fun collectReviewId(list: List<NetworkReview>) = list.map { it.id }
}

