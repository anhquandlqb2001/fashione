/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.models

import com.google.firebase.firestore.PropertyName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import vn.quanprolazer.fashione.domain.models.Video

@Serializable
data class NetworkVideo(
    val id: String = "",
    val uri: String = "",
    @SerialName("thumbnail_url")
    @get:PropertyName("thumbnail_url")
    @set:PropertyName("thumbnail_url")
    var thumbnailUrl: String = "",
    val title: String = ""
)

internal fun NetworkVideo.toDomainModel() = Video(id, uri, thumbnailUrl, title)