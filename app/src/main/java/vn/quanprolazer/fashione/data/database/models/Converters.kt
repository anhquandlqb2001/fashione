/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.database.models

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import vn.quanprolazer.fashione.domain.models.NotificationOverview
import java.util.*

class Converters {

    @TypeConverter
    fun notificationOverviewToJson(list: List<NotificationOverview>): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun jsonToNotificationOverview(list: String): List<NotificationOverview> {
        return Json.decodeFromString(list)
    }

    @TypeConverter
    fun productMostViewToJson(list: List<String>): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun jsonToProductMostView(data: String): List<String> {
        return Json.decodeFromString(data)
    }

    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}