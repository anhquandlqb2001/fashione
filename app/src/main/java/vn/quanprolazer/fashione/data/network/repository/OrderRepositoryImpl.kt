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
import vn.quanprolazer.fashione.data.domain.model.AddToCartItem
import vn.quanprolazer.fashione.data.domain.model.CartItem
import vn.quanprolazer.fashione.data.domain.model.Result
import vn.quanprolazer.fashione.data.domain.repository.OrderRepository
import vn.quanprolazer.fashione.data.domain.repository.UserRepository
import vn.quanprolazer.fashione.data.network.service.OrderService
import vn.quanprolazer.fashione.data.network.mapper.NetworkCartItemMapper

class OrderRepositoryImpl @AssistedInject constructor(private val orderService: OrderService,
                                                      private val userRepository: UserRepository,
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
            is Result.Success -> Result.Success(NetworkCartItemMapper.map(result.data))
            is Result.Error -> Result.Error(result.exception)
        }
    }
}