/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.services.firestores

import kotlinx.coroutines.flow.Flow
import vn.quanprolazer.fashione.data.network.models.NetworkVideo

interface VideoService {
    fun getLiveVideos(): Flow<List<NetworkVideo>>
}