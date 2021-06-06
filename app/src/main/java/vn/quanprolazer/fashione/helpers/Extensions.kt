/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.helpers

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONObject
import vn.quanprolazer.fashione.presentation.utilities.toMap

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return kotlin.math.round(this * multiplier) / multiplier
}


/**
 * Use to convert before safe to Firestore
 */
inline fun <reified T> T.toHashMap() = JSONObject(Json.encodeToString(this)).toMap()