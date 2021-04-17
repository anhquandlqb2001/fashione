/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.repository

import com.google.firebase.firestore.Source
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vn.quanprolazer.fashione.data.domain.model.Product
import vn.quanprolazer.fashione.data.domain.model.ProductDetail
import vn.quanprolazer.fashione.data.domain.model.ProductVariant
import vn.quanprolazer.fashione.data.domain.model.Result
import vn.quanprolazer.fashione.data.domain.repository.ProductRepository
import vn.quanprolazer.fashione.data.network.mapper.*
import vn.quanprolazer.fashione.data.network.service.ProductService
import vn.quanprolazer.fashione.data.network.service.SearchServiceImpl

class ProductRepositoryImpl(
    private val productService: ProductService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ProductRepository {

    override suspend fun getProducts(source: Source): Result<List<Product>> {

        val response = withContext(dispatcher) {
            productService.getProducts(source)
        }

        return when (response) {
            is Result.Success -> {
                Result.Success(NetworkProductListMapper.map(response.data))
            }
            is Result.Error -> Result.Error(response.exception)
        }
    }

    override suspend fun getProductsByCategoryId(categoryId: String): List<Product> {
        return withContext(dispatcher) {
            NetworkProductListMapper.map(productService.getProductsByCategoryId(categoryId))
        }
    }

    override suspend fun findProductsByQuery(query: String): List<Product> {
        return withContext(dispatcher) {
            NetworkProductListAlgoliaMapper.map(SearchServiceImpl.findProductsByQuery(query))
        }
    }

    override suspend fun getProductDetailByProductId(productId: String): ProductDetail {
        return withContext(dispatcher) {
            NetworkProductDetailMapper.map(productService.getProductDetailByProductId(productId))
        }
    }

    override suspend fun getProductVariantsAndOptionsByProductId(productId: String): List<ProductVariant> {
        return withContext(dispatcher) {
            val productVariants = mutableListOf<ProductVariant>()
            val networkProductVariants = productService.getProductVariantsByProductId(productId)

            for (networkVariant in networkProductVariants) {
                val productVariant = ProductVariant(networkVariant.id, networkVariant.name)
                productVariant.options = NetworkProductVariantOptionsMapper.map(
                    productService.getProductVariantOptionsByProductVariantId(networkVariant.id)
                )
                productVariants.add(productVariant)
            }
            productVariants
        }
    }
}

