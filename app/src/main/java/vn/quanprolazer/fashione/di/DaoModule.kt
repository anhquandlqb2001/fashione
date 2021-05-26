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
import vn.quanprolazer.fashione.data.database.databases.NotificationDatabase

@InstallIn(SingletonComponent::class)
@Module
class DaoModule {

    @Provides
    fun provideNotificationOverviewDao(notificationDatabase: NotificationDatabase): NotificationOverviewDao =
        notificationDatabase.notificationOverviewDao()

}