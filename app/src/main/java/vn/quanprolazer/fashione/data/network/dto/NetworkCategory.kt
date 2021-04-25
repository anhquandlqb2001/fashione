/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.dto

import com.google.firebase.firestore.DocumentId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCategory(
    @SerialName("id")
    @DocumentId
    val id: String = "Invalid id",
    @SerialName("name")
    val name: String = "Invalid category name"
)