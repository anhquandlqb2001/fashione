/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import vn.quanprolazer.fashione.domain.models.NotificationType
import vn.quanprolazer.fashione.domain.models.NotificationTypeEnum

@Serializable
enum class NetworkNotificationTypeEnum {
    @SerialName("ORDER_STATUS")
    ORDER_STATUS,

    @SerialName("PROMOTION")
    PROMOTION
}

internal fun NetworkNotificationTypeEnum.toDomainModel() = NotificationTypeEnum.valueOf(this.name)

@Serializable
data class NetworkNotificationTypeFirestore(
    val id: String = "",
    val name: NetworkNotificationTypeEnum = NetworkNotificationTypeEnum.ORDER_STATUS,
    val description: String = ""
)

internal fun NetworkNotificationTypeFirestore.toDomainModel() = NotificationType(
    id, name.toDomainModel(), description
)