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

class Converters {

    @TypeConverter
    fun listToJson(list: List<NotificationOverview>): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun jsonToList(list: String): List<NotificationOverview> {
        return Json.decodeFromString(list)
    }
}