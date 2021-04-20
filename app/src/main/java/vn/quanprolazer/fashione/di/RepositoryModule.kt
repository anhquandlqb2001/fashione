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
import vn.quanprolazer.fashione.data.domain.repository.CategoryRepository
import vn.quanprolazer.fashione.data.domain.repository.OrderRepository
import vn.quanprolazer.fashione.data.domain.repository.ProductRepository
import vn.quanprolazer.fashione.data.domain.repository.UserRepository
import vn.quanprolazer.fashione.data.network.repository.CategoryRepositoryImpl
import vn.quanprolazer.fashione.data.network.repository.OrderRepositoryImpl
import vn.quanprolazer.fashione.data.network.repository.ProductRepositoryImpl
import vn.quanprolazer.fashione.data.network.service.CategoryService
import vn.quanprolazer.fashione.data.network.service.OrderService
import vn.quanprolazer.fashione.data.network.service.ProductService
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
    fun provideUserRepository(): UserRepository = UserRepository()

    @Singleton
    @Provides
    fun provideOrderRepository(orderService: OrderService, userRepository: UserRepository
    ): OrderRepository = OrderRepositoryImpl(orderService, userRepository)
}