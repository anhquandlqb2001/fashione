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
import vn.quanprolazer.fashione.data.network.services.firestores.*
import vn.quanprolazer.fashione.data.network.services.retrofits.PickupAddressService
import vn.quanprolazer.fashione.data.network.services.retrofits.ReviewService
import vn.quanprolazer.fashione.data.repositories.*
import vn.quanprolazer.fashione.domain.repositories.*
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
    fun provideProductRepository(
        productService: ProductService
    ): ProductRepository =
        ProductRepositoryImpl(productService)

    @Singleton
    @Provides
    fun provideUserRepository(userService: UserService): UserRepository =
        UserRepositoryImpl(userService)

    @Singleton
    @Provides
    fun provideOrderRepository(
        orderService: OrderService,
        userRepository: UserRepository
    ): OrderRepository = OrderRepositoryImpl(orderService, userRepository)

    @Singleton
    @Provides
    fun provideNetworkRepository(addressService: PickupAddressService): NetworkRepository =
        NetworkRepositoryImpl(addressService)

    @Singleton
    @Provides
    fun providePurchaseRepository(
        purchaseService: PurchaseService,
        userRepository: UserRepository
    ): PurchaseRepository = PurchaseRepositoryImpl(purchaseService, userRepository)

    @Singleton
    @Provides
    fun provideReviewRepository(
        reviewServiceRetrofit: ReviewService,
        reviewServiceFirestore: vn.quanprolazer.fashione.data.network.services.firestores.ReviewService,
        orderRepository: OrderRepository,
        userRepository: UserRepository,
    ): ReviewRepository = ReviewRepositoryImpl(reviewServiceRetrofit, reviewServiceFirestore, orderRepository, userRepository)
}