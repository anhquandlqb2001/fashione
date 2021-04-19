/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.service

import com.algolia.search.saas.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import timber.log.Timber
import vn.quanprolazer.fashione.data.network.Algolia
import vn.quanprolazer.fashione.data.network.dto.NetworkAlgoliaProduct

object SearchServiceImpl : SearchService {

    override val index = Algolia.productIndex

    override val coroutineContext = Job()

    override suspend fun findProductsByQuery(query: String): List<NetworkAlgoliaProduct> {
        return withContext(Dispatchers.Default) {
            try {
                val response = index.search(Query(query), null)
                Json { ignoreUnknownKeys = true }.decodeFromString(
                    response!!.get("hits").toString()
                )
            } catch (e: Exception) {
                Timber.e(e)
                listOf()
            }
        }
    }
}