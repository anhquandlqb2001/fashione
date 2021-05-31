/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services

import com.algolia.search.saas.Query
import kotlinx.coroutines.Job
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import vn.quanprolazer.fashione.data.network.Algolia
import vn.quanprolazer.fashione.data.network.models.NetworkAlgoliaProduct

object SearchServiceImpl : SearchService {

    override val index = Algolia.productIndex

    override val coroutineContext = Job()

    override suspend fun findProductsByQuery(query: String): List<NetworkAlgoliaProduct> {
        val response = index.search(Query(query), null)
        return Json { ignoreUnknownKeys = true }.decodeFromString(
            response!!.get("hits").toString()
        )
    }
}