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
import retrofit2.Retrofit
import vn.quanprolazer.fashione.data.network.services.SearchService
import vn.quanprolazer.fashione.data.network.services.SearchServiceImpl
import vn.quanprolazer.fashione.data.network.services.firestores.*
import vn.quanprolazer.fashione.data.network.services.retrofits.PickupAddressService
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ServiceModule {

    @Provides
    fun provideCategoryService(): CategoryService = CategoryServiceImpl()

    @Provides
    fun provideProductService(): ProductService = ProductServiceImpl()

    @Provides
    fun provideOrderService(): OrderService = OrderServiceImpl()

    @Provides
    fun provideUserService(): UserService = UserServiceImpl()

    @Provides
    fun providePurchaseService(): PurchaseService = PurchaseServiceImpl()

    @Provides
    fun provideVideoService(): VideoService = VideoServiceImpl()

    @Provides
    fun provideMessageService(): MessageService = MessageServiceImpl()

    @Provides
    fun provideSearchService(): SearchService = SearchServiceImpl

    @Provides
    @Singleton
    fun providePickupAddressService(@Named("address") retrofit: Retrofit): PickupAddressService =
        retrofit.create(PickupAddressService::class.java)

    @Provides
    @Singleton
    fun provideReviewServiceRetrofit(@Named("nodejs") retrofit: Retrofit): vn.quanprolazer.fashione.data.network.services.retrofits.ReviewService =
        retrofit.create(vn.quanprolazer.fashione.data.network.services.retrofits.ReviewService::class.java)

    @Provides
    @Singleton
    fun provideOrderServiceRetrofit(@Named("nodejs") retrofit: Retrofit): vn.quanprolazer.fashione.data.network.services.retrofits.OrderService =
        retrofit.create(vn.quanprolazer.fashione.data.network.services.retrofits.OrderService::class.java)

    @Provides
    @Singleton
    fun provideUserServiceRetrofit(@Named("nodejs") retrofit: Retrofit): vn.quanprolazer.fashione.data.network.services.retrofits.UserService =
        retrofit.create(vn.quanprolazer.fashione.data.network.services.retrofits.UserService::class.java)

    @Provides
    fun provideReviewServiceFirestore(): ReviewService = ReviewServiceImpl()

    @Provides
    fun provideNotificationServiceFirestore(): NotificationService = NotificationServiceImpl()

    @Provides
    fun provideCartServiceFirestore(): CartService = CartServiceImpl()

    @Provides
    @Singleton
    fun provideNotificationServiceRetrofit(@Named("nodejs") retrofit: Retrofit): vn.quanprolazer.fashione.data.network.services.retrofits.NotificationService =
        retrofit.create(vn.quanprolazer.fashione.data.network.services.retrofits.NotificationService::class.java)

    @Provides
    @Singleton
    fun provideProductServiceRetrofit(@Named("nodejs") retrofit: Retrofit): vn.quanprolazer.fashione.data.network.services.retrofits.ProductService =
        retrofit.create(vn.quanprolazer.fashione.data.network.services.retrofits.ProductService::class.java)
}