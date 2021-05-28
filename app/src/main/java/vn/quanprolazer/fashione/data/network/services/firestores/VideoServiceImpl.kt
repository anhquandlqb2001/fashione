/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber
import vn.quanprolazer.fashione.data.network.models.NetworkVideo

class VideoServiceImpl : VideoService {
    @ExperimentalCoroutinesApi
    override fun getLiveVideos(): Flow<List<NetworkVideo>> = callbackFlow {
        val db = FirebaseFirestore.getInstance()
        val videoDocument = db.collection("live_videos")
        val subscription = videoDocument.addSnapshotListener { snapshot, exception ->
            if (snapshot == null) offer(emptyList())
            if (exception != null) {
                Timber.e(exception)
            }
            if (snapshot != null) {
                if (!snapshot.isEmpty) {
                    val videos =
                        snapshot.documents.mapNotNull { it.toObject(NetworkVideo::class.java) }
                    offer(videos)
                }
            }
        }
        awaitClose { subscription.remove() }
    }
}