/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import vn.quanprolazer.fashione.data.network.services.firestores.MessageService
import vn.quanprolazer.fashione.domain.models.Message
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.models.toDataModel
import vn.quanprolazer.fashione.domain.repositories.MessageRepository
import vn.quanprolazer.fashione.domain.repositories.UserRepository
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageService: MessageService,
    private val userRepository: UserRepository
) : MessageRepository {

    override suspend fun sendMessage(message: Message): Resource<Boolean> {
        val user = userRepository.getUser().value ?: return Resource.Error(Exception("NOT_LOGIN"))
        return try {
            withContext(Dispatchers.Default) {
                messageService.sendMessage(
                    message.copy(username = user.displayName.orEmpty(), userId = user.uid)
                        .toDataModel()
                )
            }
            Resource.Success(true)
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }
}