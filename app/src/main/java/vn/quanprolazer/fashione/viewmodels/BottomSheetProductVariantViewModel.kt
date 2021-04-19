/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import androidx.lifecycle.*
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.data.domain.model.CartItem
import vn.quanprolazer.fashione.data.domain.model.Product
import vn.quanprolazer.fashione.data.domain.model.ProductVariant
import vn.quanprolazer.fashione.data.domain.model.Result
import vn.quanprolazer.fashione.data.domain.repository.ProductRepository
import vn.quanprolazer.fashione.data.domain.repository.UserRepository
import vn.quanprolazer.fashione.data.network.repository.OrderRepositoryImpl
import vn.quanprolazer.fashione.data.network.service.OrderServiceImpl

class BottomSheetProductVariantViewModel @AssistedInject constructor(@Assisted private val product: Product,
                                                                     private val productRepositoryImpl: ProductRepository,
                                                                     private val userRepositoryImpl: UserRepository

) : ViewModel() {

    private val _user: UserRepository.FirebaseUserLiveData by lazy {
        userRepositoryImpl.getUser()
    }

    /**
     * Variable to store available Product Variants
     * Encapsulation
     */
    private val _productVariants: MutableLiveData<List<ProductVariant>> by lazy {
        val liveData = MutableLiveData<List<ProductVariant>>()
        viewModelScope.launch {
            liveData.value =
                productRepositoryImpl.getProductVariantsAndOptionsByProductId(product.id)

        }
        return@lazy liveData
    }

    /**
     * Variable to store available Product Variants
     */
    val productVariants: LiveData<List<ProductVariant>> by lazy {
        _productVariants
    }


    /**
     * Variable to store ProductOrder to send to Cart
     * Encapsulation
     */
    private val _cartItem: MutableLiveData<CartItem> by lazy {
        MutableLiveData(CartItem(product.id, _user.value?.uid!!))
    }


    /**
     * Function to update [_cartItem] when user change variant
     * To Update VariantName
     */
    fun onChangeVariantName(name: String) {
        _cartItem.value?.variantName = name
    }

    /**
     * Function to update [_cartItem]] when user change variant
     * To Update VariantValue (Eg: size M, L,...)
     */
    fun onChangeVariantValue(value: String) {
        _cartItem.value?.variantValue = value
    }

    /**
     * Limit order quantity value
     */
    private var orderLimit = 0

    /**
     * Function to set [orderLimit]
     */
    fun setProductOrderValueLimit(value: Number) {
        orderLimit = value as Int
    }

    /**
     * Livedata val to store current order qty user choose
     */
    val orderQty: MutableLiveData<Number> by lazy {
        MutableLiveData(0)
    }

    /**
     * Function to reset [orderQty] when user change variant
     */
    fun resetOrderQty() {
        orderQty.value = 0
    }

    /**
     * Function to set [orderQty] when user click decrement button
     */
    fun onDecreaseQty() {
        if (orderQty.value!!.toInt() > 0) {
            val t = orderQty.value!!.toInt() - 1
            _cartItem.value?.quantity = t
            orderQty.value = t
        }
    }

    /**
     * Function to set [orderQty] when user click increment button
     */
    fun onIncreaseQty() {
        if (orderQty.value!!.toInt() < orderLimit) {
            val t = orderQty.value!!.toInt() + 1
            _cartItem.value?.quantity = t
            orderQty.value = t
        }
    }

    /**
     * Variable to store current price of variant
     * To store to [_cartItem]
     *
     * Encapsulation
     */
    private val _variantPrice: MutableLiveData<String> by lazy {
        MutableLiveData("0")
    }

    /**
     * Variable to store current price of variant
     * To store to [_cartItem]
     */
    val variantPrice: LiveData<String> by lazy {
        _variantPrice
    }

    /**
     * Function to update variant price, when user choose variant value
     */
    fun updateVariantPrice(price: String) {
        _variantPrice.value = price
    }

    /**
     * Function to update variant id, when user choose variant value
     */
    fun updateCartItemVariantId(variantId: String, variantOptionId: String) {
        _cartItem.value?.variantId = variantId
        _cartItem.value?.variantOptionId = variantOptionId
    }

    /**
     * Variable to store exception
     */
    private val _exceptionMessage: MutableLiveData<String> by lazy {
        MutableLiveData()
    }
    val exceptionMessage: LiveData<String> get() = _exceptionMessage

    /**
     * Variable to store success message
     */
    private val _successMessage: MutableLiveData<String> by lazy {
        MutableLiveData()
    }
    val successMessage: LiveData<String> get() = _successMessage


    /**
     * Function to call when user click Add to cart button
     * Call api to save cart item -> cart
     */
    fun onClickAddToCart() {
        updateCartItemPrice()
        viewModelScope.launch {
            when (OrderRepositoryImpl(OrderServiceImpl()).addToCart(
                _cartItem.value!!, _user.value?.uid!!
            )) {
                is Result.Success -> _successMessage.value = "Thêm sản phẩm thành công"
                is Result.Error -> _exceptionMessage.value = "Thêm sản phẩm thất bại"
            }
        }
    }


    /**
     * Function to call when user click Add to cart button
     * Update [_cartItem] price value
     */
    private fun updateCartItemPrice() {
        _cartItem.value?.price = _variantPrice.value.toString()
    }

    @AssistedInject.Factory
    interface AssistedFactory {
        fun create(product: Product): BottomSheetProductVariantViewModel
    }

    companion object {
        fun provideFactory(assistedFactory: AssistedFactory, product: Product
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(product) as T
            }
        }
    }
}