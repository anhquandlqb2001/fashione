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
import vn.quanprolazer.fashione.data.network.service.CategoryService
import vn.quanprolazer.fashione.data.network.service.CategoryServiceImpl
import vn.quanprolazer.fashione.data.network.service.ProductService
import vn.quanprolazer.fashione.data.network.service.ProductServiceImpl

@InstallIn(SingletonComponent::class)
@Module
class ServiceModule {

    @Provides
    fun provideCategoryService() : CategoryService = CategoryServiceImpl()

    @Provides
    fun provideProductService() : ProductService = ProductServiceImpl()

}