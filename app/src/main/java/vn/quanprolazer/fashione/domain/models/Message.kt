/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.models

import com.google.firebase.Timestamp
import vn.quanprolazer.fashione.data.network.models.NetworkMessage


data class Message(
    val id: String = "",
    val message: String,
    val userId: String = "",
    val username: String = "",
    var direction: MessageDirect = MessageDirect.OUTGOING,
    val createdAt: Timestamp
)

enum class MessageDirect {
    INCOMING,
    OUTGOING
}

internal fun Message.toDataModel() =
    NetworkMessage(
        message = message,
        userId = userId,
        username = username,
        createdAt = createdAt
    )