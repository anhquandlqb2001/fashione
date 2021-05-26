/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import vn.quanprolazer.fashione.domain.models.NotificationOverview
import vn.quanprolazer.fashione.domain.models.NotificationType

@Entity
data class NotificationOverviewEntity(
    @PrimaryKey
    val id: Long,
    val notifications: List<NotificationOverview>,
    val total: Int
)