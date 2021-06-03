/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */


package vn.quanprolazer.fashione.data.network.services.firestores

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import vn.quanprolazer.fashione.data.network.models.*
import java.util.*

class ProductServiceImpl : ProductService {

    companion object {
        private const val DEFAULT_HORIZONTAL_ITEM_COUNT = 10
    }

    override suspend fun getRecentProducts(): NetworkProductResponse {
        val networkProducts = FirebaseFirestore
            .getInstance()
            .collection("products")
            .orderBy("created_at", Query.Direction.DESCENDING)
            .limit((DEFAULT_HORIZONTAL_ITEM_COUNT + 1).toLong())
            .get()
            .await()
            .documents.mapNotNull {
                it.toObject((NetworkProduct::class.java))
            }
        return NetworkProductResponse(
            products = networkProducts,
            lastVisibleId = getLastVisibleDocumentId(networkProducts)
        )
    }

    override suspend fun getRecentProducts(documentId: String): NetworkProductResponse {
        val collection = FirebaseFirestore.getInstance()
            .collection("products")
        val ref = collection.document(documentId)
        val networkProducts = FirebaseFirestore
            .getInstance()
            .collection("products")
            .orderBy("created_at", Query.Direction.DESCENDING)
            .limit((DEFAULT_HORIZONTAL_ITEM_COUNT + 1).toLong())
            .startAt(ref)
            .get()
            .await()
            .documents.mapNotNull {
                it.toObject((NetworkProduct::class.java))
            }
        return NetworkProductResponse(
            products = networkProducts,
            lastVisibleId = networkProducts[DEFAULT_HORIZONTAL_ITEM_COUNT].id
        )
    }

    override suspend fun getRecentProductIds(): List<String> {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        return FirebaseFirestore
            .getInstance()
            .collection("products")
            .whereGreaterThan("created_at", Timestamp(calendar.time))
            .whereLessThan("created_at", Timestamp.now())
            .get()
            .await()
            .documents.mapNotNull {
                it.id
            }
    }

    override suspend fun getProducts(list: List<String>) =
        FirebaseFirestore.getInstance().collection("products")
            .whereIn(FieldPath.documentId(), list)
            .get()
            .await().documents.mapNotNull { it.toObject(NetworkProduct::class.java) }

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

    fun getLastVisibleDocumentId(prods: List<NetworkProduct>): String? {
        if (prods.size == DEFAULT_HORIZONTAL_ITEM_COUNT) return prods[prods.size - 1].id;
        return null
    }
}