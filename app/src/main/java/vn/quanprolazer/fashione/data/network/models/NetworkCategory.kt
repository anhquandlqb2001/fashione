/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.models

import com.google.firebase.firestore.DocumentId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import vn.quanprolazer.fashione.domain.models.Category

@Serializable
data class NetworkCategory(
    @SerialName("id")
    @DocumentId
    val id: String = "Invalid id",
    @SerialName("name")
    val name: String = "Invalid category name"
)

internal fun NetworkCategory.toDomainModel() = Category(
    id,
    name
)