/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class DeviceToken(
    val userId: String,
    val token: String
)
