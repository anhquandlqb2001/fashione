/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber
import vn.quanprolazer.fashione.data.network.models.toDomainModel
import vn.quanprolazer.fashione.data.network.services.firestores.MessageService
import vn.quanprolazer.fashione.domain.models.Message
import vn.quanprolazer.fashione.domain.models.MessageDirect
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.models.toDataModel
import vn.quanprolazer.fashione.domain.repositories.MessageRepository
import vn.quanprolazer.fashione.domain.repositories.UserRepository
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageService: MessageService,
    private val userRepository: UserRepository
) : MessageRepository {

    override suspend fun sendMessage(message: Message): Resource<Message> {
        val user = userRepository.getUser().value ?: return Resource.Error(Exception("NOT_LOGIN"))
        return try {
            return withContext(Dispatchers.Default) {
                val fullMessage =
                    message.copy(username = user.displayName.orEmpty(), userId = user.uid)
                val id = messageService.sendMessage(
                    fullMessage.toDataModel()
                )
                Resource.Success(fullMessage.copy(id = id))
            }
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }

    override suspend fun getMessages(): Resource<List<Message>> {
        val user = userRepository.getUser().value ?: return Resource.Error(Exception("NOT_LOGIN"))
        return try {
            return withContext(Dispatchers.Default) {
                val incomingMessages =
                    messageService.getIncomingMessages(user.uid).map { it.toDomainModel() }
                val outgoingMessages =
                    messageService.getOutgoingMessages(user.uid).map { it.toDomainModel() }
                val messages = incomingMessages + outgoingMessages
                val editedMessages = messages.map {
                    if (it.userId == user.uid) it.direction = MessageDirect.OUTGOING
                    else it.direction = MessageDirect.INCOMING
                    it
                }.sortedBy { it.createdAt }
                Resource.Success(editedMessages)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getRecentlyIncomingMessage(): Flow<Resource<Message>> =
        messageService.getRecentlyIncomingMessage(userRepository.getUser().value?.uid!!)
            .map { Resource.Success(it.toDomainModel()) }
}