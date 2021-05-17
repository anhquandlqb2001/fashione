/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.viewmodels

import androidx.lifecycle.*
import com.google.firebase.firestore.DocumentSnapshot
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.domain.models.*
import vn.quanprolazer.fashione.domain.repositories.ProductRepository


class ProductViewModel @AssistedInject constructor(
    private val productRepository: ProductRepository,
    @Assisted val product: Product
) : ViewModel() {


    private val _productImages by lazy {
        val liveData = MutableLiveData<Resource<List<ProductImage>>>()
        viewModelScope.launch {
            liveData.value = productRepository.getProductImagesByProductId(product.id)
        }
        return@lazy liveData
    }

    val productImages: LiveData<Resource<List<ProductImage>>> get() = _productImages

    /**
     * Variable to store data about product
     * Encapsulation
     */
    private val _productDetail by lazy {
        val liveData = MutableLiveData<Resource<ProductDetail>>()
        viewModelScope.launch {
            liveData.value = productRepository.getProductDetailByProductId(product.id)
        }
        return@lazy liveData
    }

    /**
     * Variable to store data about product
     */
    val productDetail: LiveData<Resource<ProductDetail>> get() = _productDetail


    /**
     * Variable store data pass to SafeArgs when open BottomSheetFragmentDialog
     * Encapsulation
     */
    private val _navigateToBottomSheet: MutableLiveData<Product?> by lazy {
        MutableLiveData<Product?>()
    }

    /**
     * Variable store data pass to SafeArgs when open BottomSheetFragmentDialog
     */
    val navigateToBottomSheet: LiveData<Product?> by lazy {
        _navigateToBottomSheet
    }

    /**
     * Function to update [_navigateToBottomSheet] when user click Buy button
     */
    fun onNavigateToBottomSheet(product: Product) {
        _navigateToBottomSheet.value = product
    }


    /**
     * Function to prevent UI error after navigate to another Fragment
     * Fire after navigate Fragment
     */
    fun doneNavigate() {
        _navigateToBottomSheet.value = null
    }

    val rating: MutableLiveData<Resource<List<Rating>>> by lazy {
        val response = MutableLiveData<Resource<List<Rating>>>()
        viewModelScope.launch {
            response.value = productRepository.getRatings(product.id)
        }



        return@lazy response
    }

    val overviewRating: LiveData<Resource<OverviewRating>>
        get() = Transformations.map(rating) { list ->
            when (list) {
                is Resource.Success -> {
                    val size = list.data.size
                    val avg = list.data.sumBy { it.rate } / size
                    Resource.Success(OverviewRating(avg.toFloat(), size))
                }
                else -> {
                    Resource.Error(Exception("Exception when get review"))
                }
            }
        }

    private var lastVisible: DocumentSnapshot? = null

    private val _reviewWithRatings: MutableLiveData<Resource<List<ReviewWithRating>>> by lazy {
        val liveData = MutableLiveData<Resource<List<ReviewWithRating>>>()
        viewModelScope.launch {
            val response = productRepository.getReviewWithRating(product.id, lastVisible)
            liveData.value = response.reviews
            lastVisible = response.lastVisible
        }
        return@lazy liveData
    }

    val reviewWithRatings: LiveData<Resource<List<ReviewWithRating>>> get() = _reviewWithRatings

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(product: Product): ProductViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory, product: Product
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(product) as T
            }
        }
    }
}

