/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.viewmodels

import androidx.lifecycle.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import timber.log.Timber
import vn.quanprolazer.fashione.domain.models.*
import vn.quanprolazer.fashione.domain.repositories.ReviewRepository

class ReviewViewModel @AssistedInject constructor(
    private val reviewRepository: ReviewRepository,
    @Assisted private val productId: String
) :
    ViewModel() {

    val rating: MutableLiveData<Resource<List<Rating>>> by lazy {
        val response = MutableLiveData<Resource<List<Rating>>>()
        viewModelScope.launch {
            response.value = reviewRepository.getRatings(productId)
        }
        return@lazy response
    }

    val overviewRating: LiveData<Resource<OverviewRating>>
        get() = Transformations.map(rating) { list ->
            when (list) {
                is Resource.Success -> {
                    if (list.data.isEmpty()) Resource.Success(OverviewRating(0f, 0))
                    else {
                        val size = list.data.size
                        val avg = (list.data.sumBy { it.rate }.toDouble() / size)
                        Resource.Success(OverviewRating(avg.round(2).toFloat(), size))
                    }
                }
                else -> {
                    Resource.Error(Exception("Exception when get review"))
                }
            }
        }

    private var _lastVisibleId: String? = null
    val lastVisibleId: String? get() = _lastVisibleId

    private val _reviewWithRatings: MutableLiveData<Resource<MutableList<ReviewRetrofit>>> by lazy {
        val liveData =
            MutableLiveData<Resource<MutableList<ReviewRetrofit>>>(Resource.Loading(null))
        viewModelScope.launch {
            when (val response = reviewRepository.getReviews(productId, _lastVisibleId)) {
                is Resource.Success -> {
                    liveData.value = Resource.Success(response.data.reviews.toMutableList())
                    _lastVisibleId = response.data.lastVisibleId
                }
                is Resource.Error -> {
                    liveData.value = Resource.Error(response.exception)
                    _lastVisibleId = null
                }
            }
        }
        return@lazy liveData
    }

    val reviewWithRatings: LiveData<Resource<MutableList<ReviewRetrofit>>> get() = _reviewWithRatings

    private var onFetching: Boolean = false

    fun fetchMoreReview() {
        if (lastVisibleId.isNullOrBlank() || onFetching) return
        onFetching = true
        viewModelScope.launch {
            when (val response = reviewRepository.getReviews(productId, _lastVisibleId)) {
                is Resource.Success -> {
                    (_reviewWithRatings.value as Resource.Success).data.addAll(response.data.reviews.toMutableList())
                    _reviewWithRatings.value = _reviewWithRatings.value
                    _lastVisibleId = response.data.lastVisibleId
                    onFetching = false
                }
                is Resource.Error -> {
                    _lastVisibleId = null
                }
            }
        }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(productId: String): ReviewViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory, productId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(productId) as T
            }
        }
    }
}

private fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return kotlin.math.round(this * multiplier) / multiplier
}