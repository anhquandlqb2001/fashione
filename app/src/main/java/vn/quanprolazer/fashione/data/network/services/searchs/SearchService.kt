/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */


package vn.quanprolazer.fashione.data.network.services.searchs

import com.algolia.search.saas.Index
import kotlinx.coroutines.CoroutineScope
import vn.quanprolazer.fashione.data.network.models.NetworkAlgoliaProduct

interface SearchService : CoroutineScope {

    companion object {
        private const val DEFAULT_PAGE = 0
        private const val DEFAULT_PER_PAGE = 10
    }

    val index: Index

    suspend fun findProductsByQuery(
        query: String,
        page: Int = DEFAULT_PAGE,
        perPage: Int = DEFAULT_PER_PAGE
    ): List<NetworkAlgoliaProduct>
}