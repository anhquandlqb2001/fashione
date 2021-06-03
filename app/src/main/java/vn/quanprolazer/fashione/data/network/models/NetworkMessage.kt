/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
import vn.quanprolazer.fashione.domain.models.Message
import vn.quanprolazer.fashione.domain.models.MessageDirect

data class NetworkMessage(
    @DocumentId
    @Exclude
    val id: String = "",
    val message: String = "",
    @set:PropertyName("user_id")
    @get:PropertyName("user_id")
    var userId: String = "",
    val username: String = "",
    @set:PropertyName("created_at")
    @get:PropertyName("created_at")
    var createdAt: Timestamp = Timestamp.now()
)

internal fun NetworkMessage.toDomainModel() = Message(
    id = id,
    message = message,
    userId = userId,
    username = username,
    createdAt = createdAt,
    direction = MessageDirect.OUTGOING
)