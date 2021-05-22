/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.models

enum class NotificationTypeEnum {
    ORDER_STATUS,
    PROMOTION
}

data class Notification(
    val id: String,
    val recipientId: String,
    val typeId: String,
    val data: String,
    val read: Boolean,
    val deleted: Boolean,
    val createdAt: String
)

data class NotificationType(
    val id: String,
    val name: NotificationTypeEnum,
    val description: String
)

data class NotificationOverview(
    val type: NotificationType,
    val quantity: Int
)