/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */


package vn.quanprolazer.fashione.data.network.services.firestores

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import vn.quanprolazer.fashione.data.network.models.*
import vn.quanprolazer.fashione.domain.models.Resource


class ProductServiceImpl : ProductService {


    override suspend fun getProducts(source: Source): Resource<List<NetworkProduct>> {
        val db = FirebaseFirestore.getInstance()
        return try {
            val list = db.collection("products").get(source).await().documents.mapNotNull {
                it.toObject((NetworkProduct::class.java))
            }
            Resource.Success(list)
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }

    override suspend fun getProductByProductId(productId: String): Resource<NetworkProduct> {
        val db = FirebaseFirestore.getInstance()
        return try {
            val response =
                db.collection("products").document(productId).get()
                    .await()

            Resource.Success(response.toObject(NetworkProduct::class.java)!!)
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }

    override suspend fun getProductDetailByProductId(productId: String): Resource<NetworkProductDetail> {
        val db = FirebaseFirestore.getInstance()

        return try {
            val response =
                db.collection("product_detail").whereEqualTo("product_id", productId).limit(1).get()
                    .await().documents.mapNotNull { it.toObject(NetworkProductDetail::class.java) }[0]

            Resource.Success(response)
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }

    override suspend fun getProductVariantsByProductId(productId: String): Resource<List<NetworkProductVariant>> {
        val db = FirebaseFirestore.getInstance()

        return try {
            val response =
                db.collection("product_variants").whereEqualTo("product_id", productId).get()
                    .await().documents.mapNotNull { it.toObject(NetworkProductVariant::class.java) }

            Resource.Success(response)
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }

    override suspend fun getProductVariantOptionsByVariantId(variantId: String): Resource<List<NetworkProductVariantOption>> {
        val db = FirebaseFirestore.getInstance()
        return try {
            val response =
                db.collection("product_variant_options").whereEqualTo("variant_id", variantId).get()
                    .await().documents.mapNotNull { it.toObject(NetworkProductVariantOption::class.java) }

            Resource.Success(response)
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }

    override suspend fun getProductVariantOption(variantOptionId: String): Resource<NetworkProductVariantOption> {
        val db = FirebaseFirestore.getInstance()
        return try {
            val response =
                db.collection("product_variant_options").document(variantOptionId).get()
                    .await().toObject(NetworkProductVariantOption::class.java)
                    ?: return Resource.Error(Exception("Product Variant Option not exist"))

            Resource.Success(response)
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }

    override suspend fun getProductsByCategoryId(categoryId: String): Resource<List<NetworkProduct>> {
        val db = FirebaseFirestore.getInstance()
        return try {
            val response = db.collection("products").whereEqualTo("category_id", categoryId).get()
                .await().documents.mapNotNull { it.toObject(NetworkProduct::class.java) }

            Resource.Success(response)
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }

    override suspend fun getProductImagesByProductId(productId: String): Resource<List<NetworkProductImage>> {
        val db = FirebaseFirestore.getInstance()
        return try {
            val response =
                db.collection("product_images").whereEqualTo("product_id", productId).get()
                    .await().documents.mapNotNull { it.toObject(NetworkProductImage::class.java) }

            Resource.Success(response)
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }

    override suspend fun getProductImageByProductId(productId: String): Resource<NetworkProductImage> {
        val db = FirebaseFirestore.getInstance()
        return try {
            val response =
                db.collection("product_images").whereEqualTo("product_id", productId).get()
                    .await().documents.mapNotNull { it.toObject(NetworkProductImage::class.java) }

            Resource.Success(response[0])
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }

    override suspend fun getProductImageByVariantId(variantId: String): Resource<NetworkProductImage> {
        val db = FirebaseFirestore.getInstance()
        return try {
            val response =
                db.collection("product_images").whereEqualTo("variant_id", variantId).get()
                    .await().documents.mapNotNull { it.toObject(NetworkProductImage::class.java) }

            Resource.Success(response[0])
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }

}