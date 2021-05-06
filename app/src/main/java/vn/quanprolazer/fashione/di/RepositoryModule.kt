/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import vn.quanprolazer.fashione.data.domain.repository.*
import vn.quanprolazer.fashione.data.network.repository.*
import vn.quanprolazer.fashione.data.network.service.*
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideCategoryRepository(categoryService: CategoryService): CategoryRepository =
        CategoryRepositoryImpl(categoryService)

    @Singleton
    @Provides
    fun provideProductRepository(productService: ProductService): ProductRepository =
        ProductRepositoryImpl(productService)

    @Singleton
    @Provides
    fun provideUserRepository(userService: UserService): UserRepository = UserRepositoryImpl(userService)

    @Singleton
    @Provides
    fun provideOrderRepository(orderService: OrderService, userRepository: UserRepository, productRepository: ProductRepository
    ): OrderRepository = OrderRepositoryImpl(orderService, userRepository, productRepository)

    @Singleton
    @Provides
    fun provideNetworkRepository(addressService: PickupAddressService): NetworkRepository = NetworkRepositoryImpl(addressService)
}