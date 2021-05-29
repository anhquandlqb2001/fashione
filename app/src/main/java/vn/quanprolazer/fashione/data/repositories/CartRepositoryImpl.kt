/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.repositories

import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber
import vn.quanprolazer.fashione.data.network.models.toDomainModel
import vn.quanprolazer.fashione.data.network.services.firestores.CartService
import vn.quanprolazer.fashione.data.network.services.firestores.ProductService
import vn.quanprolazer.fashione.domain.models.AddToCartItem
import vn.quanprolazer.fashione.domain.models.CartItem
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.models.toNetworkModel
import vn.quanprolazer.fashione.domain.repositories.CartRepository
import vn.quanprolazer.fashione.domain.repositories.UserRepository

class CartRepositoryImpl @AssistedInject constructor(
    private val userRepository: UserRepository,
    private val cartService: CartService,
    private val productService: ProductService,
    @Assisted private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) :
    CartRepository {

    override suspend fun addToCart(addToCartItem: AddToCartItem): Resource<Boolean> {
        return try {
            withContext(defaultDispatcher) {
                cartService.addToCart(addToCartItem, userRepository.getUser().value!!.uid)
            }
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getCartItems(): Resource<MutableList<CartItem>> {
        val user =
            userRepository.getUser().value ?: return Resource.Error(Exception("NOT_LOGIN"))

        return try {
            val cartItems = cartService.getCartItems(user.uid).map {
                val productImage =
                    productService.getProductImageByVariantId(it.variantId).toDomainModel()
                val product =
                    productService.getProductByProductId(it.productId).toDomainModel()
                val price =
                    productService.getProductVariantOption(it.variantOptionId).toDomainModel()
                return@map CartItem(
                    id = it.id,
                    productId = it.productId,
                    cartItemImg = productImage,
                    quantity = it.quantity,
                    product = product,
                    userId = it.userId,
                    variantName = it.variantName,
                    price = price.price,
                    variantValue = it.variantValue,
                    variantOptionId = it.variantOptionId,
                    variantId = it.variantId
                )
            }
            Resource.Success(cartItems.toMutableList())
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }


    override suspend fun updateCartItem(cartItemId: String, quantity: Int): Resource<Boolean> {
        return try {
            withContext(defaultDispatcher) {
                cartService.updateCartItem(cartItemId, quantity)
            }
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun removeCartItem(cartItemId: String): Resource<Boolean> {
        return try {
            withContext(defaultDispatcher) {
                cartService.removeCartItem(cartItemId)
            }
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun undoDeleteCartItem(cartItem: CartItem): Resource<Boolean> {
        return try {
            withContext(defaultDispatcher) {
                cartService.addToCart(
                    cartItem.toNetworkModel(), userRepository.getUser().value!!.uid, cartItem.id
                )
            }
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getCartItemCount(): Flow<Resource<Int>> =
        cartService.getCartItemCount(userRepository.getUser().value?.uid!!)
            .map { Resource.Success(it) }
}