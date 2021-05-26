/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.models

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

enum class NotificationTypeEnum(val text: String) {
    ORDER_STATUS("Thông báo đơn hàng"),
    ACTIVITY("Hoạt động"),
    PROMOTION("Khuyến mãi")
}

interface Notification {
    val id: String
    val recipientId: String
    val typeId: String
    val read: Boolean
    val deleted: Boolean
    val createdAt: Timestamp
    val type: NotificationTypeEnum
}

data class NotificationOrderStatus(
    override val id: String,
    override val recipientId: String,
    override val typeId: String,
    override val read: Boolean,
    override val deleted: Boolean,
    override val createdAt: Timestamp,
    override val type: NotificationTypeEnum = NotificationTypeEnum.ORDER_STATUS,
    val data: NotificationOrderDataType
): Notification

data class NotificationOrderDataType(
    val product: NotificationOrderDataProduct,
    val payload: NotificationPayload
)

data class NotificationPayload(
    val notification: NotificationPayloadData
)

data class NotificationPayloadData(
    val body: String,
    val title: String,
    val image: String
)

data class NotificationOrderDataProduct(
    val orderId: String,
    val orderItemId: String
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