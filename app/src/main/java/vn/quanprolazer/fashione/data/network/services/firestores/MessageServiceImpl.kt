/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import vn.quanprolazer.fashione.data.network.models.NetworkMessage

class MessageServiceImpl : MessageService {
    override suspend fun sendMessage(message: NetworkMessage) {
        val db = FirebaseFirestore.getInstance()
        db.collection("messages").add(message).await()
    }
}