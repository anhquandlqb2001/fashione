/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.domain.models

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

enum class NotificationTypeEnum(val text: String) {
    ORDER_STATUS("Thông báo đơn hàng"),
    ACTIVITY("Hoạt động"),
    PROMOTION("Khuyến mãi")
}

interface Notification<T> {
    val id: String
    val recipientId: String
    val typeId: String
    val read: Boolean
    val deleted: Boolean
    val createdAt: Timestamp
    val type: NotificationTypeEnum
    val data: T
}

data class NotificationExtend(
    override val id: String,
    override val recipientId: String,
    override val typeId: String,
    override val read: Boolean,
    override val deleted: Boolean,
    override val createdAt: Timestamp,
    override val type: NotificationTypeEnum = NotificationTypeEnum.PROMOTION,
    override val data: NotificationExtendData
) : Notification<NotificationExtendData>

data class NotificationExtendData(
    val payload: NotificationPayload
)

data class NotificationOrderStatus(
    override val id: String,
    override val recipientId: String,
    override val typeId: String,
    override val read: Boolean,
    override val deleted: Boolean,
    override val createdAt: Timestamp,
    override val type: NotificationTypeEnum = NotificationTypeEnum.ORDER_STATUS,
    override val data: NotificationOrderStatusData
) : Notification<NotificationOrderStatusData>

data class NotificationOrderStatusData(
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


@Serializable
@Parcelize
data class NotificationType(
    val id: String,
    val name: NotificationTypeEnum,
    val description: String,
    val imageUrl: String
) : Parcelable

@Serializable
@Parcelize
data class NotificationOverview(
    val type: NotificationType,
    val quantity: Int
) : Parcelable

data class NotificationOverviewResponse(
    val notifications: List<NotificationOverview>,
    val total: Int
)