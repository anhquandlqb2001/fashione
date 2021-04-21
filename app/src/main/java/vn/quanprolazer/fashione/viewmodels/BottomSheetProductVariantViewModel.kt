/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.viewmodels

import androidx.lifecycle.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import vn.quanprolazer.fashione.data.domain.model.*
import vn.quanprolazer.fashione.data.domain.repository.OrderRepository
import vn.quanprolazer.fashione.data.domain.repository.ProductRepository
import vn.quanprolazer.fashione.data.domain.repository.UserRepository

class BottomSheetProductVariantViewModel @AssistedInject constructor(@Assisted private val product: Product,
                                                                     private val productRepository: ProductRepository,
                                                                     private val userRepository: UserRepository,
                                                                     private val orderRepository: OrderRepository

) : ViewModel() {

    private val _user: UserRepository.FirebaseUserLiveData by lazy {
        userRepository.getUser()
    }

    /**
     * Variable to store available Product Variants
     * Encapsulation
     */
    private val _productVariants: MutableLiveData<List<ProductVariant>> by lazy {
        val liveData = MutableLiveData<List<ProductVariant>>()
        viewModelScope.launch {
            liveData.value =
                productRepository.getProductVariantsAndOptionsByProductId(product.id)

        }
        return@lazy liveData
    }

    /**
     * Variable to store available Product Variants
     */
    val productVariants: LiveData<List<ProductVariant>> get() = _productVariants


    /**
     * Variable to store ProductOrder to send to Cart
     * Encapsulation
     */
    private val _addToCartItem: MutableLiveData<AddToCartItem> by lazy {
        MutableLiveData(AddToCartItem(product.id, _user.value?.uid!!))
    }


    /**
     * Function to update [_addToCartItem] when user change variant
     * To Update VariantName
     */
    fun onChangeVariantName(name: String) {
        _addToCartItem.value?.variantName = name
    }

    /**
     * Function to update [_addToCartItem]] when user change variant
     * To Update VariantValue (Eg: size M, L,...)
     */
    fun onChangeVariantValue(value: String) {
        _addToCartItem.value?.variantValue = value
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
            _addToCartItem.value?.quantity = t
            orderQty.value = t
        }
    }

    /**
     * Function to set [orderQty] when user click increment button
     */
    fun onIncreaseQty() {
        if (orderQty.value!!.toInt() < orderLimit) {
            val t = orderQty.value!!.toInt() + 1
            _addToCartItem.value?.quantity = t
            orderQty.value = t
        }
    }

    /**
     * Variable to store current price of variant
     * To store to [_addToCartItem]
     *
     * Encapsulation
     */
    private val _variantPrice: MutableLiveData<String> by lazy {
        MutableLiveData("0")
    }

    /**
     * Variable to store current price of variant
     * To store to [_addToCartItem]
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
        _addToCartItem.value?.variantId = variantId
        _addToCartItem.value?.variantOptionId = variantOptionId
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
            when (orderRepository.addToCart(_addToCartItem.value!!)) {
                is Result.Success -> _successMessage.value = "Thêm sản phẩm thành công"
                is Result.Error -> _exceptionMessage.value = "Thêm sản phẩm thất bại"
            }
        }
    }


    /**
     * Function to call when user click Add to cart button
     * Update [_addToCartItem] price value
     */
    private fun updateCartItemPrice() {
        _addToCartItem.value?.price = _variantPrice.value.toString()
    }

    @dagger.assisted.AssistedFactory
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