/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.domain.models.*
import vn.quanprolazer.fashione.domain.repositories.OrderRepository
import vn.quanprolazer.fashione.domain.repositories.UserRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class CheckoutViewModel @AssistedInject constructor(
    private val userRepository: UserRepository,
    private val orderRepository: OrderRepository,
    @Assisted private val checkoutItemsNor: List<CheckoutItem>
) : ViewModel() {

    private val _checkoutItems: MutableLiveData<List<CheckoutItem>> by lazy {
        MutableLiveData(checkoutItemsNor)
    }

    val checkoutItems: LiveData<List<CheckoutItem>> get() = _checkoutItems

    val totalProductPrice: LiveData<String>
        get() = Transformations.map(_checkoutItems) {
            var price = "0"
            it.map { checkoutItem ->
                price =
                    (price.toFloat() + (checkoutItem.price.toFloat() * checkoutItem.quantity)).toString()
            }
            return@map price
        }

    val totalShipPrice: LiveData<String> by lazy { MutableLiveData("0") }

    private val _navigateToPickupAddress: MutableLiveData<Boolean> by lazy {
        MutableLiveData()
    }

    val navigateToPickupAddress: LiveData<Boolean> get() = _navigateToPickupAddress

    fun onNavigateToPickupAddress() {
        _navigateToPickupAddress.value = true
    }

    fun doneNavigate() {
        _navigateToPickupAddress.value = null
    }

    private val _defaultCheckoutAddress: MutableLiveData<Resource<PickupAddress>> by lazy {
        val liveData = MutableLiveData<Resource<PickupAddress>>()
        viewModelScope.launch {
            liveData.value = userRepository.getDefaultPickupAddress()
        }
        return@lazy liveData
    }
    val defaultCheckoutAddress: LiveData<Resource<PickupAddress>> get() = _defaultCheckoutAddress

    private val _pickupAddressId: MutableLiveData<String> by lazy { MutableLiveData() }
    val pickupAddressId: LiveData<String> get() = _pickupAddressId

    fun updateAddressId(addressId: String) {
        _pickupAddressId.value = addressId
    }

    private val _navigateToOrderSuccess: MutableLiveData<Resource<Boolean>> by lazy { MutableLiveData() }
    val navigateToOrderSuccess: LiveData<Resource<Boolean>> get() = _navigateToOrderSuccess

    fun doneNavigateToOrderSuccess() {
        _navigateToOrderSuccess.value = null
    }

    var shipPrice = "0"
    fun updateShipPrice(price: String) {
        shipPrice = price
    }

    var productPrice = "0"
    fun updateProductPrice(price: String) {
        productPrice = price
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onOrderClick() {
        _navigateToOrderSuccess.value = Resource.Loading

        val order = Order(
            userId = userRepository.getUser().value!!.uid,
            addressId = _pickupAddressId.value!!,
            shippingPriceTotal = shipPrice,
            productPriceTotal = productPrice,
            createdAt = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))
        )

        val orderItems = _checkoutItems.value!!.map {
            OrderItem(
                productId = it.productId,
                variantOptionId = it.variantOptionId,
                productName = it.productName,
                variantName = it.variantName,
                variantValue = it.variantValue,
                price = it.price,
                quantity = it.quantity,
                variantId = it.variantId,
                cartItemId = it.id,
                reviewStatus = ReviewStatus.NO
            )
        }

        viewModelScope.launch {
            _navigateToOrderSuccess.value = orderRepository.createOrder(order, orderItems)
        }
    }


    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(checkoutItemsNor: List<CheckoutItem>): CheckoutViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory, checkoutItemsNor: List<CheckoutItem>
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(checkoutItemsNor) as T
            }
        }
    }
}