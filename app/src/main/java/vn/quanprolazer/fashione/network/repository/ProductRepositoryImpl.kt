/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network.repository

import com.google.firebase.firestore.Source
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vn.quanprolazer.fashione.domain.model.Product
import vn.quanprolazer.fashione.domain.model.ProductDetail
import vn.quanprolazer.fashione.domain.model.Result
import vn.quanprolazer.fashione.domain.repository.ProductRepository
import vn.quanprolazer.fashione.network.mapper.ProductDetailMapper
import vn.quanprolazer.fashione.network.mapper.ProductListAlgoliaMapper
import vn.quanprolazer.fashione.network.mapper.ProductListMapper
import vn.quanprolazer.fashione.network.service.ProductService
import vn.quanprolazer.fashione.network.service.SearchServiceImpl

class ProductRepositoryImpl(
    private val productService: ProductService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ProductRepository {

    override suspend fun getProducts(source: Source): Result<List<Product>> {

        val response = withContext(dispatcher) {
            productService.getProducts(source)
        }

        return when(response) {
            is Result.Success -> { Result.Success(ProductListMapper.map(response.data)) }
            is Result.Error -> Result.Error(response.exception)
        }
    }

    override suspend fun getProductsByCategoryId(categoryId: String): List<Product> {
        return withContext(dispatcher) {
            ProductListMapper.map(productService.getProductsByCategoryId(categoryId))
        }
    }

    override suspend fun findProductsByQuery(query: String): List<Product> {
        return withContext(dispatcher) {
            ProductListAlgoliaMapper.map(SearchServiceImpl.findProductsByQuery(query))
        }
    }

    override suspend fun getProductDetailByProductId(productId: String): ProductDetail {
        return withContext(dispatcher) {
            ProductDetailMapper.map(productService.getProductDetailByProductId(productId))
        }
    }
}

