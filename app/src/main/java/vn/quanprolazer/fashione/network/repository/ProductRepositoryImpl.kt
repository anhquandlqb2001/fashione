/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vn.quanprolazer.fashione.domain.model.Product
import vn.quanprolazer.fashione.domain.model.ProductDetail
import vn.quanprolazer.fashione.domain.repository.ProductRepository
import vn.quanprolazer.fashione.network.mapper.ProductDetailMapper
import vn.quanprolazer.fashione.network.mapper.ProductListAlgoliaMapper
import vn.quanprolazer.fashione.network.mapper.ProductListMapper
import vn.quanprolazer.fashione.network.service.ProductService
import vn.quanprolazer.fashione.network.service.ProductServiceImpl
import vn.quanprolazer.fashione.network.service.SearchServiceImpl

class ProductRepositoryImpl(private val productService: ProductService) : ProductRepository {

    override suspend fun getProducts(): List<Product> {
        return ProductListMapper.map(productService.getProducts())
    }

    override suspend fun getProductsByCategoryId(categoryId: String): List<Product> {
        return ProductListMapper.map(productService.getProductsByCategoryId(categoryId))
    }

    override suspend fun findProductsByQuery(query: String): List<Product> {
        return ProductListAlgoliaMapper.map(SearchServiceImpl.findProductsByQuery(query))
    }

    override suspend fun getProductDetailByProductId(productId: String): ProductDetail {
        return ProductDetailMapper.map(productService.getProductDetailByProductId(productId))
    }
}