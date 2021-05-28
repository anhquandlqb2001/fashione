/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.repositories

import kotlinx.coroutines.flow.Flow
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.models.LiveVideo

interface VideoRepository {

    fun getLiveVideos(): Flow<Resource<List<LiveVideo>>>

}