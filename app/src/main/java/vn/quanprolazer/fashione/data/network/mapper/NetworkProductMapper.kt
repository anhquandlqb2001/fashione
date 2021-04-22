/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.mapper

import vn.quanprolazer.fashione.data.domain.model.*
import vn.quanprolazer.fashione.data.mapper.ListMapper
import vn.quanprolazer.fashione.data.mapper.Mapper
import vn.quanprolazer.fashione.data.network.dto.*

object NetworkProductMapper : Mapper<NetworkProduct, Product> {
    override fun map(input: NetworkProduct): Product {
        return Product(
            input.id.orEmpty(), input.categoryId, input.name, input.thumbnailUrl, input.price
        )
    }
}


object NetworkProductListMapper : ListMapper<NetworkProduct, Product> {
    override fun map(input: List<NetworkProduct>): List<Product> {
        return input.map {
            Product(
                it.id.orEmpty(), it.categoryId, it.name, it.thumbnailUrl, it.price
            )
        }
    }
}

object NetworkProductListAlgoliaMapper : ListMapper<NetworkAlgoliaProduct, Product> {
    override fun map(input: List<NetworkAlgoliaProduct>): List<Product> {
        return input.map {
            Product(
                it.id, it.category_id, it.name, it.thumbnail_url, it.price
            )
        }
    }
}


object NetworkProductDetailMapper : Mapper<NetworkProductDetail, ProductDetail> {
    override fun map(input: NetworkProductDetail): ProductDetail {
        return ProductDetail(
            input.id, input.productId, input.description
        )
    }
}

object NetworkProductImagesMapper : ListMapper<NetworkProductImage, ProductImage> {
    override fun map(input: List<NetworkProductImage>) =
        input.map { ProductImage(it.id, it.productId, it.variantId, it.variantOptionId, it.url) }
}

object NetworkProductImageMapper : Mapper<NetworkProductImage, ProductImage> {
    override fun map(input: NetworkProductImage) =
        ProductImage(input.id, input.productId, input.variantId, input.variantOptionId, input.url)
}

object NetworkProductVariantOptionsMapper :
    ListMapper<NetworkProductVariantOption, ProductVariantOption> {
    override fun map(input: List<NetworkProductVariantOption>) =
        input.map { ProductVariantOption(it.id, it.value, it.quantity, it.price) }
}


object NetworkProductVariantsMapper : ListMapper<NetworkProductVariant, ProductVariant> {
    override fun map(input: List<NetworkProductVariant>) =
        input.map { ProductVariant(it.id, it.name) }
}