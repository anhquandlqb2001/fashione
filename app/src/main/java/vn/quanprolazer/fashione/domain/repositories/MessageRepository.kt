/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.repositories

import vn.quanprolazer.fashione.domain.models.Message
import vn.quanprolazer.fashione.domain.models.Resource

interface MessageRepository {

    suspend fun sendMessage(message: Message): Resource<Boolean>

}