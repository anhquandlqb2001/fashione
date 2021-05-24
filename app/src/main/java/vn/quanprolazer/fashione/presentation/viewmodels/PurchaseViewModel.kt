/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.viewmodels

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.domain.models.*
import vn.quanprolazer.fashione.domain.repositories.CartRepository
import vn.quanprolazer.fashione.domain.repositories.ProductRepository
import vn.quanprolazer.fashione.domain.repositories.PurchaseRepository
import vn.quanprolazer.fashione.presentation.ui.*
import vn.quanprolazer.fashione.presentation.utilities.mapInPlace
import javax.inject.Inject

@HiltViewModel
class PurchaseViewModel @Inject constructor(
    private val purchaseRepository: PurchaseRepository,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) :
    ViewModel() {

    /**
     * Call when user select in tab bar, from Fragment
     */
    fun updatePurchaseItems(position: Number) {
        when (position) {
            CONFIRMING_POSITION -> updatePurchaseItems(OrderItemStatusType.CONFIRMING)
            COLLECTING_POSITION -> updatePurchaseItems(OrderItemStatusType.COLLECTING)
            DELIVERING_POSITION -> updatePurchaseItems(OrderItemStatusType.DELIVERING)
            DELIVERED_POSITION -> updatePurchaseItems(OrderItemStatusType.DELIVERED)
            COMPLETE_POSITION -> updatePurchaseItems(OrderItemStatusType.COMPLETE)
        }
    }

    private val _purchaseItems: MutableLiveData<Resource<MutableList<Purchase>>> by lazy {
        val liveData = MutableLiveData<Resource<MutableList<Purchase>>>(Resource.Loading(null))

        viewModelScope.launch {
            liveData.value = purchaseRepository.getPurchaseItems(OrderItemStatusType.CONFIRMING)
        }
        return@lazy liveData
    }
    val purchaseItems: LiveData<Resource<MutableList<Purchase>>> get() = _purchaseItems

    private fun updatePurchaseItems(status: OrderItemStatusType) {
        _purchaseItems.value = Resource.Loading(null)
        viewModelScope.launch {
            _purchaseItems.value = purchaseRepository.getPurchaseItems(status)
        }
    }

    fun getPurchaseItemsImage() {
        (_purchaseItems.value as Resource.Success).data.mapInPlace {
            if (it.purchaseImage != null) return
            viewModelScope.launch {
                when (val cartItemImage =
                    productRepository.getProductImageByProductId(it.productId)) {
                    is Resource.Success -> it.purchaseImage =
                        Resource.Success(cartItemImage.data)
                    is Resource.Error -> Resource.Error(cartItemImage.exception)
                    is Resource.Loading -> it.purchaseImage = null
                }
            }
            it
        }
    }

    private val _addToCartResponse: MutableLiveData<Resource<Boolean>> by lazy { MutableLiveData() }
    val addToCartResponse: LiveData<Resource<Boolean>> get() = _addToCartResponse

    fun doneNavigateToCart() {
        _addToCartResponse.value = null
    }

    fun onClickReOrder(purchase: Purchase) {
        _addToCartResponse.value = Resource.Loading(null)

        val addToCartItem = AddToCartItem(
            productId = purchase.productId,
            userId = purchase.userId,
            variantId = purchase.variantId,
            variantOptionId = purchase.variantOptionId,
            variantName = purchase.variantName,
            variantValue = purchase.variantValue,
            quantity = purchase.quantity
        )
        viewModelScope.launch {
            _addToCartResponse.value = cartRepository.addToCart(addToCartItem)
        }
    }

    var isDialogShowing = false
    fun doneShowingDialog() {
        isDialogShowing = true
    }

    private val _navigateToAddReview: MutableLiveData<PurchaseToAddReview> by lazy { MutableLiveData() }
    val navigateToAddReview: LiveData<PurchaseToAddReview> get() = _navigateToAddReview
    fun onClickNavigateToAddReview(purchase: Purchase) {
        _navigateToAddReview.value = PurchaseToAddReview(
            Product(
                id = purchase.productId,
                name = purchase.productName,
                price = purchase.price,
                thumbnailUrl = (purchase.purchaseImage as Resource.Success).data.url
            ),
            ProductVariantOption(
                id = purchase.variantOptionId,
                value = purchase.variantValue,
                quantity = purchase.quantity,
                price = purchase.price
            ),
            (purchase.purchaseImage as Resource.Success).data,
            orderItemId = purchase.id,
            variantName = purchase.variantName
        )
    }

    fun doneNavigateToAddReview() {
        _navigateToAddReview.value = null
    }

}