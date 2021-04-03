/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network.repository

import vn.quanprolazer.fashione.domain.model.Product
import vn.quanprolazer.fashione.domain.model.ProductDetail
import vn.quanprolazer.fashione.domain.repository.ProductRepository
import vn.quanprolazer.fashione.network.mapper.ProductDetailMapper
import vn.quanprolazer.fashione.network.mapper.ProductListAlgoliaMapper
import vn.quanprolazer.fashione.network.mapper.ProductListMapper
import vn.quanprolazer.fashione.network.service.ProductServiceImpl
import vn.quanprolazer.fashione.network.service.SearchServiceImpl

object ProductRepositoryImpl : ProductRepository {

    override suspend fun getProducts(): List<Product> {
        return ProductListMapper.map(ProductServiceImpl.getProducts())
    }

    override suspend fun getProductsByCategoryId(categoryId: String): List<Product> {
        return ProductListMapper.map(ProductServiceImpl.getProductsByCategoryId(categoryId))
    }

    override suspend fun findProductsByQuery(query: String): List<Product> {
        return ProductListAlgoliaMapper.map(SearchServiceImpl.findProductsByQuery(query))
    }

    override suspend fun getProductDetailByProductId(productId: String): ProductDetail {
        return ProductDetailMapper.map(ProductServiceImpl.getProductDetailByProductId(productId))
    }
}