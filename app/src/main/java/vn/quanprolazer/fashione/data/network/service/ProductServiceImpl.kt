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
            val list = db.collection("products").get(source).await().documents.mapNotNull {
                it.toObject((NetworkProduct::class.java))
            }
            Result.Success(list)
        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(e)
        }
    }

    override suspend fun getProductByProductId(productId: String): Result<NetworkProduct> {
        val db = FirebaseFirestore.getInstance()
        return try {
            val response =
                db.collection("products").document(productId).get()
                    .await()

            Result.Success(response.toObject(NetworkProduct::class.java)!!)
        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(e)
        }
    }

    override suspend fun getProductDetailByProductId(productId: String): Result<NetworkProductDetail> {
        val db = FirebaseFirestore.getInstance()

        return try {
            val response =
                db.collection("product_detail").whereEqualTo("product_id", productId).limit(1).get()
                    .await().documents.mapNotNull { it.toObject(NetworkProductDetail::class.java) }[0]

            Result.Success(response)
        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(e)
        }
    }

    override suspend fun getProductVariantsByProductId(productId: String): Result<List<NetworkProductVariant>> {
        val db = FirebaseFirestore.getInstance()

        return try {
            val response =
                db.collection("product_variants").whereEqualTo("product_id", productId).get()
                    .await().documents.mapNotNull { it.toObject(NetworkProductVariant::class.java) }

            Result.Success(response)
        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(e)
        }
    }

    override suspend fun getProductVariantOptionsByVariantId(variantId: String): Result<List<NetworkProductVariantOption>> {
        val db = FirebaseFirestore.getInstance()
        return try {
            val response =
                db.collection("product_variant_options").whereEqualTo("variant_id", variantId).get()
                    .await().documents.mapNotNull { it.toObject(NetworkProductVariantOption::class.java) }

            Result.Success(response)
        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(e)
        }
    }

    override suspend fun getProductsByCategoryId(categoryId: String): Result<List<NetworkProduct>> {
        val db = FirebaseFirestore.getInstance()
        return try {
            val response = db.collection("products").whereEqualTo("category_id", categoryId).get()
                .await().documents.mapNotNull { it.toObject(NetworkProduct::class.java) }

            Result.Success(response)
        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(e)
        }
    }

    override suspend fun getProductImagesByProductId(productId: String): Result<List<NetworkProductImage>> {
        val db = FirebaseFirestore.getInstance()
        return try {
            val response =
                db.collection("product_images").whereEqualTo("product_id", productId).get()
                    .await().documents.mapNotNull { it.toObject(NetworkProductImage::class.java) }

            Result.Success(response)
        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(e)
        }
    }

    override suspend fun getProductImageByVariantId(variantId: String): Result<NetworkProductImage> {
        val db = FirebaseFirestore.getInstance()
        return try {
            val response =
                db.collection("product_images").whereEqualTo("variant_id", variantId).get()
                    .await().documents.mapNotNull { it.toObject(NetworkProductImage::class.java) }

            Result.Success(response[0])
        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(e)
        }
    }
}