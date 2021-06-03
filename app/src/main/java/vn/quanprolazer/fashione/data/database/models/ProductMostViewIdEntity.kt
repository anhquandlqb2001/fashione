/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import java.util.*

@Entity
data class ProductMostViewIdEntity(
    @PrimaryKey
    val id: Long,
    val ids: List<String>,
    @SerialName(value = "last_update")
    var lastUpdate: Date = Date(System.currentTimeMillis())
)