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
import vn.quanprolazer.fashione.data.database.dao.ProductMostViewIdDao
import vn.quanprolazer.fashione.data.database.models.ProductMostViewIdEntity
import vn.quanprolazer.fashione.data.network.models.NetworkRating
import vn.quanprolazer.fashione.data.network.models.toDomainModel
import vn.quanprolazer.fashione.data.network.services.searchs.SearchService
import vn.quanprolazer.fashione.data.network.services.firestores.ProductService
import vn.quanprolazer.fashione.data.network.services.firestores.ReviewService
import vn.quanprolazer.fashione.domain.models.*
import vn.quanprolazer.fashione.domain.repositories.ProductRepository
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.round

class ProductRepositoryImpl @AssistedInject constructor(
    private val productService: ProductService,
    private val reviewService: ReviewService,
    private val productRetrofitService: vn.quanprolazer.fashione.data.network.services.retrofits.ProductService,
    private val productMostViewIdDao: ProductMostViewIdDao,
    private val searchService: SearchService,
    @Assisted private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ProductRepository {

    override suspend fun getRecentProducts(documentId: String?) = try {
        val response = if (documentId.isNullOrEmpty()) {
            withContext(dispatcher) {
                productService.getRecentProducts()
            }
        } else {
            withContext(dispatcher) {
                productService.getRecentProducts(documentId = documentId)
            }
        }
        Resource.Success(
            ProductResponse(
                products = response.products.map { it.toDomainModel() }.toMutableList(),
                lastVisibleId = response.lastVisibleId
            )
        )
    } catch (e: Exception) {
        Resource.Error(e)
    }

    override suspend fun getHighRatingProducts(): Resource<List<Product>> {
        return try {
            withContext(dispatcher) {
                val recentProductIds = productService.getRecentProductIds()
                if (recentProductIds.isNullOrEmpty()) return@withContext Resource.Success(listOf<Product>())
                val productWithRates = recentProductIds.map { productId ->
                    val rate = calculateAverageRating(
                        reviewService.getRatings(
                            productId
                        )
                    )
                    ProductWithRate(id = productId, rate = rate)
                }.sortedBy { it.rate }
                val products = productService.getProducts(productWithRates.map { it.id })
                Resource.Success(products.map { it.toDomainModel() })
            }
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }

    private fun calculateAverageRating(list: List<NetworkRating>) =
        round((list.sumBy { it.rate }.toDouble() / list.size))


    override suspend fun getProductsByCategoryId(categoryId: String) = try {
        val response = withContext(dispatcher) {
            productService.getProductsByCategoryId(categoryId)
        }
        Resource.Success(response.map { it.toDomainModel() })
    } catch (e: Exception) {
        Resource.Error(e)
    }

    override suspend fun getHighViewProducts() = try {
        withContext(dispatcher) {
            refreshMostViewProductIds()
            val ids = productMostViewIdDao.loadProductMostView().ids
            Resource.Success(productService.getProducts(ids).map { it.toDomainModel() })
        }
    } catch (e: Exception) {
        Resource.Error(e)
    }

    private suspend fun refreshMostViewProductIds() {
        val idExists = productMostViewIdDao.hasId(System.currentTimeMillis().minus(FRESH_TIMEOUT))
        if (idExists == 0) {
            withContext(dispatcher) {
                val recentProductIds = productRetrofitService.getHighViewProductIds().ids
                productMostViewIdDao.save(
                    ProductMostViewIdEntity(
                        id = Long.MAX_VALUE,
                        ids = recentProductIds
                    )
                )
            }
        }

    }

    companion object {
        val FRESH_TIMEOUT = TimeUnit.DAYS.toMillis(1)
    }

    override suspend fun findProductsByQuery(query: String) = try {
        val response = withContext(dispatcher) {
            searchService.findProductsByQuery(query)
        }
        val productIds = response.map { it.id }
        val networkProducts = productService.getProducts(productIds)
        Resource.Success(networkProducts.map { it.toDomainModel() })
    } catch (e: Exception) {
        Timber.e(e)
        Resource.Error(e)
    }

    override suspend fun getProductDetailByProductId(productId: String) = try {
        val response = withContext(dispatcher) {
            productService.getProductDetailByProductId(productId)
        }
        Resource.Success(response.toDomainModel())
    } catch (e: Exception) {
        Resource.Error(e)
    }

    override suspend fun getProductVariantsByProductId(productId: String) = try {
        val response = withContext(dispatcher) {
            productService.getProductVariantsByProductId(productId)
        }
        Resource.Success(response.map { it.toDomainModel() }.toMutableList())
    } catch (e: Exception) {
        Resource.Error(e)
    }

    override suspend fun getProductVariantOptionsByVariantId(variantId: String) = try {
        val response = withContext(dispatcher) {
            productService.getProductVariantOptionsByVariantId(variantId)
        }
        Resource.Success(response.map { it.toDomainModel() })
    } catch (e: Exception) {
        Resource.Error(e)
    }

    override suspend fun getProductImagesByProductId(productId: String) = try {
        val response = withContext(dispatcher) {
            productService.getProductImagesByProductId(productId)
        }
        Resource.Success(response.map { it.toDomainModel() })
    } catch (e: Exception) {
        Resource.Error(e)
    }

    override suspend fun getProductImageByProductId(productId: String) = try {
        val response = withContext(dispatcher) {
            productService.getProductImageByProductId(productId)
        }
        Resource.Success(response.toDomainModel())
    } catch (e: Exception) {
        Resource.Error(e)
    }

    override suspend fun getProductImageByProductVariantId(variantId: String) = try {
        val response = withContext(dispatcher) {
            productService.getProductImageByVariantId(variantId)
        }
        Resource.Success(response.toDomainModel())
    } catch (e: Exception) {
        Resource.Error(e)
    }

    override suspend fun getProductByProductId(productId: String): Resource<Product> {
        return try {
            Resource.Success(withContext(dispatcher) {
                (productService.getProductByProductId(productId))
            }.toDomainModel())
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }


    override suspend fun getProductVariantOption(variantOptionId: String): Resource<ProductVariantOption> {
        return try {
            Resource.Success(withContext(dispatcher) {
                productService.getProductVariantOption(variantOptionId)
            }.toDomainModel())
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }
}

