/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services

import com.algolia.search.saas.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import timber.log.Timber
import vn.quanprolazer.fashione.data.network.Algolia
import vn.quanprolazer.fashione.data.network.models.NetworkAlgoliaProduct
import vn.quanprolazer.fashione.domain.models.Resource

object SearchServiceImpl : SearchService {

    override val index = Algolia.productIndex

    override val coroutineContext = Job()

    override suspend fun findProductsByQuery(query: String): Resource<List<NetworkAlgoliaProduct>> {
        return withContext(Dispatchers.Default) {
            try {
                val response = index.search(Query(query), null)
                val decodedResponse: List<NetworkAlgoliaProduct> =
                    Json { ignoreUnknownKeys = true }.decodeFromString(
                        response!!.get("hits").toString()
                    )
                Resource.Success(decodedResponse)
            } catch (e: Exception) {
                Timber.e(e)
                Resource.Error(e)
            }
        }
    }
}