/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.service

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import vn.quanprolazer.fashione.data.domain.model.Result
import vn.quanprolazer.fashione.data.network.dto.*

class ProductServiceImpl : ProductService {
    override suspend fun getProducts(source: Source): Result<List<NetworkProduct>> {
        val db = FirebaseFirestore.getInstance()
        return try {
            val list = db.collection("products")
                .get(source)
                .await()
                .documents
                .mapNotNull {
                    it.toObject((NetworkProduct::class.java))
                }
            Result.Success(list)
        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(e)
        }
    }

    override suspend fun getProductDetailByProductId(productId: String): NetworkProductDetail {
        val db = FirebaseFirestore.getInstance()

        return try {
            return db.collection("product_detail")
                .whereEqualTo("product_id", productId)
                .limit(1)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(NetworkProductDetail::class.java) }[0]
        } catch (e: Exception) {
            Timber.e(e)
            NetworkProductDetail()
        }
    }

    override suspend fun getProductVariantsByProductId(productId: String): List<NetworkProductVariant> {
        val db = FirebaseFirestore.getInstance()

        return try {
            return db.collection("product_variants")
                .whereEqualTo("product_id", productId)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(NetworkProductVariant::class.java) }
        } catch (e: Exception) {
            Timber.e(e)
            listOf(NetworkProductVariant())
        }
    }

    override suspend fun getProductVariantOptionsByProductVariantId(variantId: String): List<NetworkProductVariantOption> {
        val db = FirebaseFirestore.getInstance()

        return try {
            return db.collection("product_variant_options")
                .whereEqualTo("variant_id", variantId)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(NetworkProductVariantOption::class.java) }
        } catch (e: Exception) {
            Timber.e(e)
            listOf(NetworkProductVariantOption())
        }
    }

    override suspend fun getProductsByCategoryId(categoryId: String): List<NetworkProduct> {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("products")
                .whereEqualTo("category_id", categoryId)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(NetworkProduct::class.java) }
        } catch (e: Exception) {
            Timber.e(e)
            listOf()
        }
    }
}