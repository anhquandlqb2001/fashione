/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import vn.quanprolazer.fashione.data.database.models.NotificationOverviewEntity

@Dao
interface NotificationOverviewDao {
    @Insert(onConflict = REPLACE)
    fun save(notificationOverview: NotificationOverviewEntity)

    @Query("SELECT * FROM NotificationOverviewEntity")
    fun loadNotificationOverviews(): NotificationOverviewEntity
}