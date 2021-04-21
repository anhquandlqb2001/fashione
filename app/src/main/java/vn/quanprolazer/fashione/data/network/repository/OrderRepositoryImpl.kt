/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.repository

import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import vn.quanprolazer.fashione.data.domain.model.AddToCartItem
import vn.quanprolazer.fashione.data.domain.model.CartItem
import vn.quanprolazer.fashione.data.domain.model.ProductImage
import vn.quanprolazer.fashione.data.domain.model.Result
import vn.quanprolazer.fashione.data.domain.repository.OrderRepository
import vn.quanprolazer.fashione.data.domain.repository.ProductRepository
import vn.quanprolazer.fashione.data.domain.repository.UserRepository
import vn.quanprolazer.fashione.data.network.mapper.NetworkCartItemsMapper
import vn.quanprolazer.fashione.data.network.service.OrderService
import java.lang.Exception

class OrderRepositoryImpl @AssistedInject constructor(private val orderService: OrderService,
                                                      private val userRepository: UserRepository,
                                                      private val productRepository: ProductRepository,
                                                      @Assisted private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : OrderRepository {
    override suspend fun addToCart(addToCartItem: AddToCartItem): Result<Boolean> {

        //        if (userRepository.getAuthenticateState().value != AuthenticationState.AUTHENTICATED) {
        //            return Result.Error(Exception("User not login yet"))
        //        }

        val result = withContext(defaultDispatcher) {
            orderService.addToCart(addToCartItem, userRepository.getUser().value!!.uid)
        }


        return when (result) {
            is Result.Success -> Result.Success(true)
            is Result.Error -> Result.Error(result.exception)
        }
    }

    override suspend fun getCartItems(): Result<List<CartItem>> {
        //        if (userRepository.getAuthenticateState().value != AuthenticationState.AUTHENTICATED) {
        //            return Result.Error(Exception("User not login yet"))
        //        }

        val result = withContext(defaultDispatcher) {
            orderService.getCartItems(userRepository.getUser().value!!.uid)
        }



        return when (result) {
            is Result.Success -> Result.Success(NetworkCartItemsMapper.map(result.data))
            is Result.Error -> Result.Error(result.exception)
        }
    }
}

//    Result.Success(result.data.map {
//        when (val img = withContext(Dispatchers.IO) {
//            productRepository.getProductImageByProductVariantId(it.variantId)
//        }) {
//            is Result.Success -> {
//                CartItem(
//                    it.id,
//                    it.productId,
//                    it.userId,
//                    it.variantId,
//                    it.variantOptionId,
//                    it.variantName,
//                    it.variantValue,
//                    it.quantity,
//                    it.price,
//                    img.data
//                )
//            }
//            else -> {
//                CartItem(
//                    it.id,
//                    it.productId,
//                    it.userId,
//                    it.variantId,
//                    it.variantOptionId,
//                    it.variantName,
//                    it.variantValue,
//                    it.quantity,
//                    it.price,
//                    ProductImage("", "", "", "", "")
//                )
//            }
//        }
//
//    })
//}