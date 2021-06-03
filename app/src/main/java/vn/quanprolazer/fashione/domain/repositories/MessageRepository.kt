/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.repositories

import kotlinx.coroutines.flow.Flow
import vn.quanprolazer.fashione.domain.models.Message
import vn.quanprolazer.fashione.domain.models.Resource

interface MessageRepository {

    suspend fun sendMessage(message: Message): Resource<Message>

    suspend fun getMessages(): Resource<List<Message>>

    suspend fun getRecentlyIncomingMessage(): Flow<Resource<Message>>

}