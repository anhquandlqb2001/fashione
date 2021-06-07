/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.searchs

import com.algolia.search.saas.Query
import kotlinx.coroutines.Job
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import vn.quanprolazer.fashione.data.network.models.NetworkAlgoliaProduct
import vn.quanprolazer.fashione.helpers.Algolia

object SearchServiceImpl : SearchService {

    override val index = Algolia.productIndex

    override val coroutineContext = Job()

    override suspend fun findProductsByQuery(
        query: String,
        page: Int,
        perPage: Int
    ): List<NetworkAlgoliaProduct> {
        val response = index.search(Query(query).setPage(page).setHitsPerPage(perPage), null)
        return Json { ignoreUnknownKeys = true }.decodeFromString(
            response!!.get("hits").toString()
        )
    }
}