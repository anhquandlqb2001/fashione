/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.models.Video
import vn.quanprolazer.fashione.domain.repositories.VideoRepository
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(private val videoRepository: VideoRepository) :
    ViewModel() {

    private val _liveVideos: MutableLiveData<Resource<List<Video>>> by lazy {
        MutableLiveData(
            Resource.Loading(null)
        )
    }
    val liveVideos: LiveData<Resource<List<Video>>> get() = _liveVideos

    init {
        viewModelScope.launch {
            videoRepository.getLiveVideos()
                .catch { e -> _liveVideos.value = Resource.Error(e as Exception) }
                .collect {
                    _liveVideos.value = it
                }
        }
    }
}