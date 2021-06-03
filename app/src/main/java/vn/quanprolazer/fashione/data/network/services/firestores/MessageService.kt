/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import kotlinx.coroutines.flow.Flow
import vn.quanprolazer.fashione.data.network.models.NetworkMessage

interface MessageService {
    suspend fun sendMessage(message: NetworkMessage): String

    suspend fun getOutgoingMessages(userId: String): List<NetworkMessage>

    suspend fun getIncomingMessages(userId: String): List<NetworkMessage>

    suspend fun getRecentlyIncomingMessage(userId: String): Flow<NetworkMessage>
}