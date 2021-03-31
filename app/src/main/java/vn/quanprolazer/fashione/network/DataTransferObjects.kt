package vn.quanprolazer.fashione.network

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import vn.quanprolazer.fashione.domain.Category
import vn.quanprolazer.fashione.domain.Product


/**
 * Product get from cloud
 */
data class NetworkProduct(
    @SerializedName("id")
    val id: String?,
    @SerializedName("categoryId")
    val categoryId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("imgUrl")
    val imgUrl: String,
    @SerializedName("price")
    val price: String
) {
    companion object {
        /**
         * Convert network result to domain object
         */
        fun DocumentSnapshot.asDomainProduct(): Product? {
            return try {
                val productName = getString("name")!!
                val productImageUrl = getString("imgUrl")!!
                val productPrice = getString("price")!!
                val categoryId = getString("categoryId")!!
                Product(id, categoryId, productName, productImageUrl, productPrice)
            } catch (e: Exception) {
                Log.e(TAG, "Error converting product", e)
                null
            }
        }

        fun DocumentSnapshot.asNetworkProduct(): NetworkProduct? {
            return try {
                val productName = getString("name")!!
                val productImageUrl = getString("imgUrl")!!
                val productPrice = getString("price")!!
                val categoryId = getString("categoryId")!!
                NetworkProduct(id, categoryId, productName, productImageUrl, productPrice)
            } catch (e: Exception) {
                Log.e(TAG, "Error converting to network product", e)
                null
            }
        }

        private const val TAG = "NetworkProduct"
    }
}


/**
 * Convert Network product to domain product
 */
fun NetworkProduct.asDomainProduct() = Product(
    this.id!!,
    categoryId,
    name,
    imgUrl,
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
                val name = requireNotNull(getString("categoryName"))
                Category(id, name)
            } catch (e: Exception) {
                Log.e(TAG, "Error converting category profile", e)
                null
            }
        }

        private const val TAG = "Category"
    }
}