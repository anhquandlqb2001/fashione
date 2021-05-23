/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

enum class NotificationTypeEnum(val text: String) {
    ORDER_STATUS("Thông báo đơn hàng"),
    ACTIVITY("Hoạt động"),
    PROMOTION("Khuyến mãi")
}

data class Notification(
    val id: String,
    val recipientId: String,
    val typeId: String,
    val data: NotificationOrderDataType,
    val read: Boolean,
    val deleted: Boolean,
    val createdAt: String,
)

data class NotificationOrderDataType(
    val imageUrl: String,
    val title: String,
    val body: String
)

@Parcelize
data class NotificationType(
    val id: String,
    val name: NotificationTypeEnum,
    val description: String,
    val imageUrl: String
) : Parcelable

@Parcelize
data class NotificationOverview(
    val type: NotificationType,
    val quantity: Int
) : Parcelable

data class NotificationOverviewResponse(
    val notifications: List<NotificationOverview>,
    val total: Int
)