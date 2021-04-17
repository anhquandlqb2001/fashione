/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network.mapper

import vn.quanprolazer.fashione.domain.model.*
import vn.quanprolazer.fashione.network.dto.*

object NetworkProductListMapper : ListMapper<NetworkProduct, Product> {
    override fun map(input: List<NetworkProduct>): List<Product> {
        return input.map {
            Product(
                it.id.orEmpty(),
                it.categoryId,
                it.name,
                it.thumbnailUrl,
                it.price,
                ProductDetail(),
                listOf(ProductVariant())
            )
        }
    }
}

object NetworkProductListAlgoliaMapper : ListMapper<NetworkAlgoliaProduct, Product> {
    override fun map(input: List<NetworkAlgoliaProduct>): List<Product> {
        return input.map {
            Product(
                it.id,
                it.category_id,
                it.name,
                it.thumbnail_url,
                it.price,
                ProductDetail(),
                listOf(ProductVariant())
            )
        }
    }
}


object NetworkProductDetailMapper : Mapper<NetworkProductDetail, ProductDetail> {
    override fun map(input: NetworkProductDetail): ProductDetail {
        return ProductDetail(
            input.id,
            input.productId,
            input.description,
            NetworkProductImagesMapper.map(input.images)
        )
    }
}

object NetworkProductImagesMapper : ListMapper<NetworkProductImage, ProductImage> {
    override fun map(input: List<NetworkProductImage>) = input.map { ProductImage(it.url) }
}

object NetworkProductVariantOptionsMapper : ListMapper<NetworkProductVariantOption, ProductVariantOption> {
    override fun map(input: List<NetworkProductVariantOption>) =
        input.map { ProductVariantOption(it.id, it.value, it.quantity, it.price) }
}
