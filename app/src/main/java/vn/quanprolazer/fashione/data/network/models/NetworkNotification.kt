/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import vn.quanprolazer.fashione.domain.models.NotificationOverview
import vn.quanprolazer.fashione.domain.models.NotificationType
import vn.quanprolazer.fashione.domain.models.NotificationTypeEnum

@Serializable
enum class NetworkNotificationTypeEnum {
    @SerialName("ORDER_STATUS")
    ORDER_STATUS,

    @SerialName("ACTIVITY")
    ACTIVITY,

    @SerialName("PROMOTION")
    PROMOTION
}

internal fun NetworkNotificationTypeEnum.toDomainModel() = NotificationTypeEnum.valueOf(this.name)

@Serializable
data class NetworkNotificationType(
    val id: String,
    val name: NetworkNotificationTypeEnum = NetworkNotificationTypeEnum.ORDER_STATUS,
    val description: String,
    @SerialName("image_url")
    val imageUrl: String
)

internal fun NetworkNotificationType.toDomainModel() = NotificationType(
    id, name.toDomainModel(), description, imageUrl = imageUrl
)

@Serializable
data class NetworkNotificationOverview(
    val type: NetworkNotificationType,
    val quantity: Int
)

@Serializable
data class NetworkNotificationOverviewResponse(
    val notifications: List<NetworkNotificationOverview>,
    val total: Int
)

internal fun NetworkNotificationOverview.toDomainModel() = NotificationOverview(
    type.toDomainModel(), quantity
)