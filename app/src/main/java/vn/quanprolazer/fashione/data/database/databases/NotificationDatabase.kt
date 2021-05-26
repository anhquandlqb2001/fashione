/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.database.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import vn.quanprolazer.fashione.data.database.dao.NotificationOverviewDao
import vn.quanprolazer.fashione.data.database.models.Converters
import vn.quanprolazer.fashione.data.database.models.NotificationOverviewEntity

@Database(entities = [NotificationOverviewEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class NotificationDatabase : RoomDatabase() {
    abstract fun notificationOverviewDao(): NotificationOverviewDao
}