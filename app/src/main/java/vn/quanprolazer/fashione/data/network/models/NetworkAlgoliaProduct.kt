/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import vn.quanprolazer.fashione.domain.models.Product

@Serializable
data class NetworkAlgoliaProduct(
    @SerialName("id")
    val id: String = "",
    @SerialName("category_ids")
    val category_ids: List<String> = listOf(),
    @SerialName("name")
    val name: String = "",
    @SerialName("thumbnail_url")
    val thumbnail_url: String = "",
    @SerialName("price")
    val price: String = ""
)

internal fun NetworkAlgoliaProduct.toDomainModel() = Product(
    id, category_ids, name, thumbnail_url, price
)