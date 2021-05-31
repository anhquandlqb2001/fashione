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
import vn.quanprolazer.fashione.data.network.models.toDomainModel
import vn.quanprolazer.fashione.data.network.services.SearchServiceImpl
import vn.quanprolazer.fashione.data.network.services.firestores.ProductService
import vn.quanprolazer.fashione.domain.models.Product
import vn.quanprolazer.fashione.domain.models.ProductVariantOption
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.repositories.ProductRepository

class ProductRepositoryImpl @AssistedInject constructor(
    private val productService: ProductService,
    @Assisted private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ProductRepository {

    override suspend fun getProducts() = try {
        val response = withContext(dispatcher) {
            productService.getProducts()
        }
        Resource.Success(response.map { it.toDomainModel() })
    } catch (e: Exception) {
        Resource.Error(e)
    }

    override suspend fun getProductsByCategoryId(categoryId: String) = try {
        val response = withContext(dispatcher) {
            productService.getProductsByCategoryId(categoryId)
        }
        Resource.Success(response.map { it.toDomainModel() })
    } catch (e: Exception) {
        Resource.Error(e)
    }

    override suspend fun findProductsByQuery(query: String) = try {
        val response = withContext(dispatcher) {
            SearchServiceImpl.findProductsByQuery(query)
        }
        Resource.Success(response.map { it.toDomainModel() })
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
        Resource.Success(mutableListOf(response[0].toDomainModel()))
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

