/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services

import com.algolia.search.saas.Index
import kotlinx.coroutines.CoroutineScope
import vn.quanprolazer.fashione.data.network.models.NetworkAlgoliaProduct

interface SearchService : CoroutineScope {

    val index: Index

    suspend fun findProductsByQuery(query: String): List<NetworkAlgoliaProduct>
}