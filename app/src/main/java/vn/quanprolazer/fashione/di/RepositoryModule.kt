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
import vn.quanprolazer.fashione.data.database.dao.NotificationOverviewDao
import vn.quanprolazer.fashione.data.database.dao.ProductMostViewIdDao
import vn.quanprolazer.fashione.data.network.services.searchs.SearchService
import vn.quanprolazer.fashione.data.network.services.firestores.*
import vn.quanprolazer.fashione.data.network.services.retrofits.NotificationService
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
        productService: ProductService,
        reviewServiceFirestore: vn.quanprolazer.fashione.data.network.services.firestores.ReviewService,
        productRetrofitService: vn.quanprolazer.fashione.data.network.services.retrofits.ProductService,
        productMostViewIdDao: ProductMostViewIdDao,
        searchService: SearchService
    ): ProductRepository =
        ProductRepositoryImpl(
            productService,
            reviewServiceFirestore,
            productRetrofitService,
            productMostViewIdDao,
            searchService
        )

    @Singleton
    @Provides
    fun provideUserRepository(
        userService: UserService,
        userRetrofitService: vn.quanprolazer.fashione.data.network.services.retrofits.UserService
    ): UserRepository =
        UserRepositoryImpl(userService, userRetrofitService)

    @Singleton
    @Provides
    fun provideOrderRepository(
        orderService: OrderService,
        userRepository: UserRepository,
        orderRetrofitService: vn.quanprolazer.fashione.data.network.services.retrofits.OrderService
    ): OrderRepository = OrderRepositoryImpl(orderService, userRepository, orderRetrofitService)

    @Singleton
    @Provides
    fun provideNetworkRepository(addressService: PickupAddressService): NetworkRepository =
        NetworkRepositoryImpl(addressService)

    @Singleton
    @Provides
    fun providePurchaseRepository(
        purchaseService: PurchaseService,
        userRepository: UserRepository,
        reviewServiceFirestore: vn.quanprolazer.fashione.data.network.services.firestores.ReviewService
    ): PurchaseRepository =
        PurchaseRepositoryImpl(purchaseService, userRepository, reviewServiceFirestore)

    @Singleton
    @Provides
    fun provideReviewRepository(
        reviewServiceRetrofit: ReviewService,
        reviewServiceFirestore: vn.quanprolazer.fashione.data.network.services.firestores.ReviewService,
        userRepository: UserRepository,
        orderRetrofitService: vn.quanprolazer.fashione.data.network.services.retrofits.OrderService
    ): ReviewRepository = ReviewRepositoryImpl(
        reviewServiceRetrofit,
        reviewServiceFirestore,
        userRepository,
        orderRetrofitService
    )

    @Singleton
    @Provides
    fun provideNotificationRepository(
        notificationServiceRetrofit: NotificationService,
        userRepository: UserRepository,
        notificationServiceFirestore: vn.quanprolazer.fashione.data.network.services.firestores.NotificationService,
        notificationOverviewDao: NotificationOverviewDao
    ): NotificationRepository = NotificationRepositoryImpl(
        notificationServiceRetrofit,
        notificationServiceFirestore,
        userRepository,
        notificationOverviewDao
    )

    @Singleton
    @Provides
    fun provideCartRepository(
        userRepository: UserRepository,
        cartService: CartService,
        productService: ProductService
    ): CartRepository = CartRepositoryImpl(
        userRepository, cartService, productService
    )

    @Singleton
    @Provides
    fun provideVideoRepository(
        videoService: VideoService
    ): VideoRepository = VideoRepositoryImpl(
        videoService
    )

    @Singleton
    @Provides
    fun provideMessageRepository(
        messageService: MessageService,
        userRepository: UserRepository
    ): MessageRepository = MessageRepositoryImpl(
        messageService, userRepository
    )

}