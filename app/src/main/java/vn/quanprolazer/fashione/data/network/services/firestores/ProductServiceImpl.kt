/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */


package vn.quanprolazer.fashione.data.network.services.firestores

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import vn.quanprolazer.fashione.data.network.models.*


class ProductServiceImpl : ProductService {

    override suspend fun getProducts() =
        FirebaseFirestore.getInstance().collection("products").get().await().documents.mapNotNull {
            it.toObject((NetworkProduct::class.java))
        }


    override suspend fun getProductByProductId(productId: String) =
        FirebaseFirestore.getInstance().collection("products").document(productId).get()
            .await().toObject(NetworkProduct::class.java)!!


    override suspend fun getProductDetailByProductId(productId: String) =
        FirebaseFirestore.getInstance().collection("product_detail")
            .whereEqualTo("product_id", productId).limit(1).get()
            .await().documents.mapNotNull { it.toObject(NetworkProductDetail::class.java) }[0]


    override suspend fun getProductVariantsByProductId(productId: String) =
        FirebaseFirestore.getInstance().collection("product_variants")
            .whereEqualTo("product_id", productId).get()
            .await().documents.mapNotNull { it.toObject(NetworkProductVariant::class.java) }


    override suspend fun getProductVariantOptionsByVariantId(variantId: String) =
        FirebaseFirestore.getInstance().collection("product_variant_options")
            .whereEqualTo("variant_id", variantId).get()
            .await().documents.mapNotNull { it.toObject(NetworkProductVariantOption::class.java) }


    override suspend fun getProductVariantOption(variantOptionId: String) =
        FirebaseFirestore.getInstance().collection("product_variant_options")
            .document(variantOptionId).get()
            .await().toObject(NetworkProductVariantOption::class.java)!!


    override suspend fun getProductsByCategoryId(categoryId: String) =
        FirebaseFirestore.getInstance().collection("products")
            .whereArrayContains("category_ids", categoryId).get()
            .await().documents.mapNotNull { it.toObject(NetworkProduct::class.java) }


    override suspend fun getProductImagesByProductId(productId: String) =
        FirebaseFirestore.getInstance().collection("product_images")
            .whereEqualTo("product_id", productId).get()
            .await().documents.mapNotNull { it.toObject(NetworkProductImage::class.java) }


    override suspend fun getProductImageByProductId(productId: String) =
        FirebaseFirestore.getInstance().collection("product_images")
            .whereEqualTo("product_id", productId).get()
            .await().documents.mapNotNull { it.toObject(NetworkProductImage::class.java) }[0]


    override suspend fun getProductImageByVariantId(variantId: String) =
        FirebaseFirestore.getInstance().collection("product_images")
            .whereEqualTo("variant_id", variantId).get()
            .await().documents.mapNotNull { it.toObject(NetworkProductImage::class.java) }[0]

}