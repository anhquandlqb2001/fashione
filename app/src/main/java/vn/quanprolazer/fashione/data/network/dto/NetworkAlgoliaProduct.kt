/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkAlgoliaProduct(
    @SerialName("id")
    val id: String = "",
    @SerialName("category_id")
    val category_id: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("thumbnail_url")
    val thumbnail_url: String = "",
    @SerialName("price")
    val price: String = ""
)