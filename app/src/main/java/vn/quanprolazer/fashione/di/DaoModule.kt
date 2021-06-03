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
import vn.quanprolazer.fashione.data.database.databases.NotificationDatabase
import vn.quanprolazer.fashione.data.database.databases.ProductDatabase

@InstallIn(SingletonComponent::class)
@Module
class DaoModule {

    @Provides
    fun provideNotificationOverviewDao(notificationDatabase: NotificationDatabase): NotificationOverviewDao =
        notificationDatabase.notificationOverviewDao()

    @Provides
    fun provideProductDao(productDatabase: ProductDatabase): ProductMostViewIdDao =
        productDatabase.productMostViewIdDao()

}