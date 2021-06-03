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
import vn.quanprolazer.fashione.domain.models.PurchaseToAddReview
import vn.quanprolazer.fashione.domain.models.Rating
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.models.Review
import vn.quanprolazer.fashione.domain.repositories.ReviewRepository
import vn.quanprolazer.fashione.presentation.utilities.LiveDataValidator
import vn.quanprolazer.fashione.presentation.utilities.LiveDataValidatorResolver

class WriteReviewViewModel @AssistedInject constructor(
    private val reviewRepository: ReviewRepository,
    @Assisted val purchaseToAddReview: PurchaseToAddReview
) : ViewModel() {

    val reviewTitle = MutableLiveData<String>()
    val reviewTitleValidator =
        LiveDataValidator(reviewTitle).apply { //Whenever the condition of the predicate is true, the error message should be emitted
            addRule("Thêm tiêu đề nhận xét") { it.isNullOrBlank() }
            addRule("Tiêu đề quá ngắn") { it?.length!! < 10 }
        }

    val reviewContent = MutableLiveData<String>()
    val reviewContentValidator =
        LiveDataValidator(reviewContent).apply { //Whenever the condition of the predicate is true, the error message should be emitted
            addRule("Thêm nội dung nhận xét") { it.isNullOrBlank() }
            addRule("Nhận xét quá ngắn") { it?.length!! < 10 }
        }

    val rating = MutableLiveData<String>()
    private val ratingValidator =
        LiveDataValidator(rating).apply { //Whenever the condition of the predicate is true, the error message should be emitted
            addRule("") { it?.toInt() == 0 }
        }


    val isAddReviewFormValidMediator: MediatorLiveData<Boolean> by lazy {
        val liveDataMediator = MediatorLiveData<Boolean>()
        liveDataMediator.value = false

        liveDataMediator.addSource(reviewContent) { validateForm() }
        liveDataMediator.addSource(reviewTitle) { validateForm() }
        liveDataMediator.addSource(rating) { validateForm() }
        liveDataMediator
    }

    //This is called whenever the form fields changes
    private fun validateForm() {
        val validators = listOf(reviewTitleValidator, reviewContentValidator, ratingValidator)
        val validatorResolver = LiveDataValidatorResolver(validators)
        isAddReviewFormValidMediator.value = validatorResolver.isValid()
    }

    private val _addReviewStatus: MutableLiveData<Resource<Boolean>> by lazy { MutableLiveData() }
    val addReviewStatus: LiveData<Resource<Boolean>> get() = _addReviewStatus

    fun doneAddReview() {
        _addReviewStatus.value = null
    }

    var isDialogShowing = false

    fun onClickAddReview() {
        _addReviewStatus.value = Resource.Loading

        val review = Review(
            productId = purchaseToAddReview.product.id,
            orderItemId = purchaseToAddReview.orderItemId,
            reviewTitle = reviewTitle.value!!,
            reviewContent = reviewContent.value!!
        )
        val _rating = Rating(
            productId = purchaseToAddReview.product.id,
            rate = rating.value!!.toInt()
        )
        viewModelScope.launch {
            _addReviewStatus.value = reviewRepository.addReview(review, _rating)
        }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(purchaseToAddReview: PurchaseToAddReview): WriteReviewViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory, purchaseToAddReview: PurchaseToAddReview
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(purchaseToAddReview) as T
            }
        }
    }
}