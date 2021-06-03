/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import vn.quanprolazer.fashione.data.network.models.NetworkMessage

class MessageServiceImpl : MessageService {
    override suspend fun sendMessage(message: NetworkMessage): String {
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("messages").add(message).await()
        return ref.id
    }

    override suspend fun getOutgoingMessages(userId: String) =
        FirebaseFirestore.getInstance().collection("messages").whereEqualTo("user_id", userId)
            .orderBy("created_at", Query.Direction.ASCENDING).get()
            .await().documents.mapNotNull { it.toObject(NetworkMessage::class.java) }

    override suspend fun getIncomingMessages(userId: String) =
        FirebaseFirestore.getInstance().collection("messages").whereEqualTo("receiver_id", userId)
            .orderBy("created_at", Query.Direction.ASCENDING).get()
            .await().documents.mapNotNull { it.toObject(NetworkMessage::class.java) }

    @ExperimentalCoroutinesApi
    override suspend fun getRecentlyIncomingMessage(userId: String): Flow<NetworkMessage> =
        callbackFlow {
            val db = FirebaseFirestore.getInstance()
            val subscription = db.collection("messages").whereEqualTo("receiver_id", userId)
                .addSnapshotListener { snapshot, exception ->
                    if (exception != null) {
                        Timber.e(exception)
                        return@addSnapshotListener
                    }
                    if (snapshot?.documentChanges.isNullOrEmpty() || snapshot?.documentChanges!!.size > 1) {
                        return@addSnapshotListener
                    }
                    if (!snapshot.isEmpty) {
                        offer(snapshot.documentChanges[0].document.toObject(NetworkMessage::class.java))
                    }
                }
            awaitClose { subscription.remove() }
        }
}
