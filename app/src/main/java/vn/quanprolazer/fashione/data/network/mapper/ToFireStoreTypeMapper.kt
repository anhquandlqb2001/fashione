/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.mapper

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONObject
import vn.quanprolazer.fashione.utilities.toMap

/**
 * Use to convert before safe to Firestore
 */
inline fun <reified T> T.toHashMap() = JSONObject(Json.encodeToString(this)).toMap()