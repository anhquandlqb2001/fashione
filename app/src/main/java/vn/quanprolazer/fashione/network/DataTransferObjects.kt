/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.network

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Transient
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject
import vn.quanprolazer.fashione.domain.*
import java.net.URLEncoder

/**
 * Product get from cloud
 */
@Serializable
data class NetworkProduct(
    @SerialName("id")
    val id: String?,
    @SerialName("category_id")
    val category_id: String,
    @SerialName("name")
    val name: String,
    @SerialName("thumbnail_url")
    val thumbnail_url: String,
    @SerialName("price")
    val price: String
) {
    companion object {
        /**
         * Convert network result to domain object
         */
        fun DocumentSnapshot.asDomainProduct(): Product? {
            return try {
                val categoryId = getString("category_id")!!
                val productName = getString("name")!!
                val productThumbnailUrl = getString("thumbnail_url")!!
                val productPrice = getString("price")!!
                Product(id, categoryId, productName, productThumbnailUrl, productPrice)
            } catch (e: Exception) {
                Log.e(TAG, "Error converting product", e)
                null
            }
        }

        fun DocumentSnapshot.asNetworkProduct(): NetworkProduct? {
            return try {
                val categoryId = getString("category_id")!!
                val name = getString("name")!!
                val thumbnailUrl = getString("thumbnail_url")!!
                val price = getString("price")!!
                NetworkProduct(id, categoryId, name, thumbnailUrl, price)
            } catch (e: Exception) {
                Log.e(TAG, "Error converting to network product", e)
                null
            }
        }

        private const val TAG = "NetworkProduct"
    }
}

data class NetworkProductDetail(
    val id: String?,
    val product_id: String,
    val quantity: Int,
    val description: String,
    val images: List<NetworkProductImage>,
    val variants: List<NetworkProductVariant>
) {
    companion object {
        fun DocumentSnapshot.asDomainProductDetail(): ProductDetail? {
            return try {
                val productId = getString("product_id")!!
                val quantity = get("quantity")!!
                val description = getString("description")!!

                val variants = get("variants")!!
                val productVariantJson = (variants as List<*>).map { JSONObject(it.toString()) }
                val productVariant = Json.decodeFromString<List<NetworkProductVariant>>(productVariantJson.toString())

                val images = get("images")!!
                val productImage = (images as List<*>).map { NetworkProductImage(it.toString()) }

                ProductDetail(
                    id,
                    productId,
                    quantity.toString().toInt(),
                    description,
                    productImage.map { it.asDomainProductImage() },
                    productVariant.map { it.asDomainProductVariant() }
                )
            } catch (e: Exception) {
                Log.e("NetworkProductDetail", "Error converting product", e)
                null
            }
        }
    }
}

@Serializable
data class NetworkProductImage(
    val url: String
)

fun NetworkProductImage.asDomainProductImage() = ProductImage(
    this.url
)
@Serializable
data class NetworkProductVariant(val color: String, val size: String, val qty: Int)

fun NetworkProductVariant.asDomainProductVariant() = ProductVariant(
    this.size,
    this.color,
    this.qty
)

/**
 * Convert Network product to domain product
 */
fun NetworkProduct.asDomainProduct() = Product(
    this.id!!,
    category_id,
    name,
    thumbnail_url,
    price
)

/**
 * Products get from Algolia search
 *
 *  {
"hits": [
{
"firstname": "Jimmie",
"lastname": "Barninger",
"objectID": "433",
"_highlightResult": {
"firstname": {
"value": "&lt;em&gt;Jimmie&lt;/em&gt;",
"matchLevel": "partial"
},
"lastname": {
"value": "Barninger",
"matchLevel": "none"
},
"company": {
"value": "California &lt;em&gt;Paint&lt;/em&gt; & Wlpaper Str",
"matchLevel": "partial"
}
}
}
],
"page": 0,
"nbHits": 1,
"nbPages": 1,
"hitsPerPage": 20,
"processingTimeMS": 1,
"query": "jimmie paint",
"params": "query=jimmie+paint&attributesToRetrieve=firstname,lastname&hitsPerPage=50"
}

 */
data class NetworkAlgoliaProductContainer(
    @SerializedName("hits")
    val products: List<NetworkProduct>
) {
    companion object {
        fun fromJson(json: String): NetworkAlgoliaProductContainer {
            return Gson().fromJson(json, NetworkAlgoliaProductContainer::class.java)
        }
    }
}


/**
 * Category get from cloud
 */
data class NetworkCategory(
    val name: String
) {
    companion object {
        /**
         * Convert network result to domain object
         */
        fun DocumentSnapshot.asDomainCategory(): Category? {
            return try {
                val name = requireNotNull(getString("name"))
                Category(id, name)
            } catch (e: Exception) {
                Log.e(TAG, "Error converting category profile", e)
                null
            }
        }

        private const val TAG = "Category"
    }
}