/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.repositories

import com.google.firebase.firestore.Source
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vn.quanprolazer.fashione.data.network.models.toDomainModel
import vn.quanprolazer.fashione.data.network.services.SearchServiceImpl
import vn.quanprolazer.fashione.data.network.services.firestores.ProductService
import vn.quanprolazer.fashione.domain.models.*
import vn.quanprolazer.fashione.domain.repositories.ProductRepository

class ProductRepositoryImpl @AssistedInject constructor(
    private val productService: ProductService,
    @Assisted private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ProductRepository {

    override suspend fun getProducts(): Resource<List<Product>> {

        val response = withContext(dispatcher) {
            productService.getProducts()
        }

        return when (response) {
            is Resource.Success -> Resource.Success(response.data.map { it.toDomainModel() })
            is Resource.Loading -> Resource.Loading(null)
            is Resource.Error -> Resource.Error(response.exception)
        }
    }

    override suspend fun getProductsByCategoryId(categoryId: String): Resource<List<Product>> {
        val response = withContext(dispatcher) {
            productService.getProductsByCategoryId(categoryId)
        }

        return when (response) {
            is Resource.Success -> Resource.Success(response.data.map { it.toDomainModel() })
            is Resource.Loading -> Resource.Loading(null)
            is Resource.Error -> Resource.Error(response.exception)
        }
    }

    override suspend fun findProductsByQuery(query: String): Resource<List<Product>> {
        val response = withContext(dispatcher) {
            SearchServiceImpl.findProductsByQuery(query)
        }

        return when (response) {
            is Resource.Success -> Resource.Success(response.data.map { it.toDomainModel() })
            is Resource.Loading -> Resource.Loading(null)
            is Resource.Error -> Resource.Error(response.exception)
        }
    }

    override suspend fun getProductDetailByProductId(productId: String): Resource<ProductDetail> {
        val response = withContext(dispatcher) {
            productService.getProductDetailByProductId(productId)
        }

        return when (response) {
            is Resource.Success -> Resource.Success(response.data.toDomainModel())
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
                response.data.map { it.toDomainModel() }.toMutableList()
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
            is Resource.Success -> Resource.Success(response.data.map { it.toDomainModel() })
            is Resource.Error -> Resource.Error(response.exception)
            else -> Resource.Loading(null)
        }
    }

    override suspend fun getProductImagesByProductId(productId: String): Resource<List<ProductImage>> {
        val response = withContext(dispatcher) {
            productService.getProductImagesByProductId(productId)
        }

        return when (response) {
            is Resource.Success -> Resource.Success(response.data.map { it.toDomainModel() })
            is Resource.Loading -> Resource.Loading(null)
            is Resource.Error -> Resource.Error(response.exception)
        }
    }

    override suspend fun getProductImageByProductId(productId: String): Resource<ProductImage> {
        val response = withContext(dispatcher) {
            productService.getProductImageByProductId(productId)
        }

        return when (response) {
            is Resource.Success -> Resource.Success(response.data.toDomainModel())
            is Resource.Loading -> Resource.Loading(null)
            is Resource.Error -> Resource.Error(response.exception)
        }
    }

    override suspend fun getProductImageByProductVariantId(variantId: String): Resource<ProductImage> {
        val result = withContext(dispatcher) {
            (productService.getProductImageByVariantId(variantId))
        }

        return when (result) {
            is Resource.Success -> Resource.Success(result.data.toDomainModel())
            is Resource.Error -> Resource.Error(result.exception)
            else -> Resource.Loading(null)
        }
    }

    override suspend fun getProductByProductId(productId: String): Resource<Product> {
        val result = withContext(dispatcher) {
            (productService.getProductByProductId(productId))
        }

        return when (result) {
            is Resource.Success -> Resource.Success(result.data.toDomainModel())
            is Resource.Error -> Resource.Error(result.exception)
            else -> Resource.Loading(null)
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
}

