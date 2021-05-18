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
import vn.quanprolazer.fashione.data.network.services.firestores.*
import vn.quanprolazer.fashione.data.network.services.retrofits.PickupAddressService
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
    @Singleton
    fun providePickupAddressService(retrofit: Retrofit): PickupAddressService =
        retrofit.create(PickupAddressService::class.java)

    @Provides
    fun provideReviewServiceService(): ReviewService = ReviewServiceImpl()
}