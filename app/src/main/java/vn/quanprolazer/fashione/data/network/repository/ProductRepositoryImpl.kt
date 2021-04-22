/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.repository

import com.google.firebase.firestore.Source
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vn.quanprolazer.fashione.data.domain.model.*
import vn.quanprolazer.fashione.data.domain.repository.ProductRepository
import vn.quanprolazer.fashione.data.network.dto.NetworkProductVariant
import vn.quanprolazer.fashione.data.network.mapper.*
import vn.quanprolazer.fashione.data.network.service.ProductService
import vn.quanprolazer.fashione.data.network.service.SearchServiceImpl
import java.lang.Exception

class ProductRepositoryImpl @AssistedInject constructor(private val productService: ProductService,
                                                        @Assisted private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ProductRepository {

    override suspend fun getProducts(source: Source): Result<List<Product>> {

        val response = withContext(dispatcher) {
            productService.getProducts(source)
        }

        return when (response) {
            is Result.Success -> Result.Success(NetworkProductListMapper.map(response.data))
            is Result.Loading -> Result.Loading(null)
            is Result.Error -> Result.Error(response.exception)
        }
    }

    override suspend fun getProductsByCategoryId(categoryId: String): Result<List<Product>> {
        val response = withContext(dispatcher) {
            productService.getProductsByCategoryId(categoryId)
        }

        return when (response) {
            is Result.Success -> Result.Success(NetworkProductListMapper.map(response.data))
            is Result.Loading -> Result.Loading(null)
            is Result.Error -> Result.Error(response.exception)
        }
    }

    override suspend fun findProductsByQuery(query: String): Result<List<Product>> {
        val response = withContext(dispatcher) {
            SearchServiceImpl.findProductsByQuery(query)
        }

        return when (response) {
            is Result.Success -> Result.Success(NetworkProductListAlgoliaMapper.map(response.data))
            is Result.Loading -> Result.Loading(null)
            is Result.Error -> Result.Error(response.exception)
        }
    }

    override suspend fun getProductDetailByProductId(productId: String): Result<ProductDetail> {
        val response = withContext(dispatcher) {
            productService.getProductDetailByProductId(productId)
        }

        return when (response) {
            is Result.Success -> Result.Success(NetworkProductDetailMapper.map(response.data))
            is Result.Loading -> Result.Loading(null)
            is Result.Error -> Result.Error(response.exception)
        }

    }

    override suspend fun getProductVariantsByProductId(productId: String): Result<MutableList<ProductVariant>> {
        val response = withContext(dispatcher) {
            productService.getProductVariantsByProductId(productId)
        }

        return when (response) {
            is Result.Success -> Result.Success(
                NetworkProductVariantsMapper.map(response.data).toMutableList()
            )
            is Result.Loading -> Result.Loading(null)
            is Result.Error -> Result.Error(response.exception)
        }
    }

    override suspend fun getProductVariantOptionsByVariantId(variantId: String): Result<List<ProductVariantOption>> {
        val response = withContext(dispatcher) {
            productService.getProductVariantOptionsByVariantId(variantId)
        }
        return when (response) {
            is Result.Success -> Result.Success(NetworkProductVariantOptionsMapper.map(response.data))
            is Result.Error -> Result.Error(response.exception)
            else -> Result.Loading(null)
        }
    }

    override suspend fun getProductImagesByProductId(productId: String): Result<List<ProductImage>> {
        val response = withContext(dispatcher) {
            productService.getProductImagesByProductId(productId)
        }

        return when (response) {
            is Result.Success -> Result.Success(NetworkProductImagesMapper.map(response.data))
            is Result.Loading -> Result.Loading(null)
            is Result.Error -> Result.Error(response.exception)
        }
    }

    override suspend fun getProductImageByProductVariantId(variantId: String): Result<ProductImage> {
        val result = withContext(dispatcher) {
            (productService.getProductImageByVariantId(variantId))
        }

        return when (result) {
            is Result.Success -> Result.Success(NetworkProductImageMapper.map(result.data))
            is Result.Error -> Result.Error(result.exception)
            else -> Result.Loading(null)
        }
    }

    override suspend fun getProductByProductId(productId: String): Result<Product> {
        val result = withContext(dispatcher) {
            (productService.getProductByProductId(productId))
        }

        return when (result) {
            is Result.Success -> Result.Success(NetworkProductMapper.map(result.data))
            is Result.Error -> Result.Error(result.exception)
            else -> Result.Loading(null)
        }
    }
}

