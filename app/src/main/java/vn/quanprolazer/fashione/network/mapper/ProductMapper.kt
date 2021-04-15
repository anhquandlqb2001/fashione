/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network.mapper

import vn.quanprolazer.fashione.domain.model.*
import vn.quanprolazer.fashione.network.dto.*

object ProductListMapper : ListMapper<NetworkProduct, Product> {
    override fun map(input: List<NetworkProduct>): List<Product> {
        return input.map {
            Product(
                it.id.orEmpty(),
                it.categoryId,
                it.name,
                it.thumbnailUrl,
                it.price,
                ProductDetailMapper.map(it.detail)
            )
        }
    }
}

object ProductListAlgoliaMapper : ListMapper<NetworkAlgoliaProduct, Product> {
    override fun map(input: List<NetworkAlgoliaProduct>): List<Product> {
        return input.map {
            Product(
                it.id,
                it.category_id,
                it.name,
                it.thumbnail_url,
                it.price,
                ProductDetail()
            )
        }
    }
}


object ProductDetailMapper : Mapper<NetworkProductDetail, ProductDetail> {
    override fun map(input: NetworkProductDetail): ProductDetail {
        return ProductDetail(
            input.id,
            input.product_id,
            input.qty,
            input.description,
            ProductImagesMapper.map(input.images),
            ProductVariantsMapper.map(input.variants)
        )
    }
}

object ProductImagesMapper : ListMapper<NetworkProductImage, ProductImage> {
    override fun map(input: List<NetworkProductImage>) = input.map { ProductImage(it.url) }
}

object ProductVariantsMapper : ListMapper<NetworkProductVariant, ProductVariant> {
    override fun map(input: List<NetworkProductVariant>) =
        input.map { ProductVariant(it.name, it.options.mapNotNull { option -> ProductVariantOption(option.value, option.qty, option.price) }) }
}
