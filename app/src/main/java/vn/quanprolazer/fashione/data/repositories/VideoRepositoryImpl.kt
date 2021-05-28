/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.repositories

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import vn.quanprolazer.fashione.data.network.models.toDomainModel
import vn.quanprolazer.fashione.data.network.services.firestores.VideoService
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.models.Video
import vn.quanprolazer.fashione.domain.repositories.VideoRepository

class VideoRepositoryImpl(private val videoService: VideoService) : VideoRepository {
    @ExperimentalCoroutinesApi
    override fun getLiveVideos(): Flow<Resource<List<Video>>> = videoService.getLiveVideos()
        .map { videos -> (Resource.Success(videos.map { it.toDomainModel() })) }

}