/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.data.network.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import vn.quanprolazer.fashione.domain.models.*

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
    val id: String = "",
    val name: NetworkNotificationTypeEnum = NetworkNotificationTypeEnum.ORDER_STATUS,
    val description: String = "",
    @SerialName("image_url")
    val imageUrl: String = ""
)

internal fun NetworkNotificationType.toDomainModel() = NotificationType(
    id, name.toDomainModel(), description, imageUrl = imageUrl
)

interface NetworkNotification {
    val id: String
    val recipientId: String
    val typeId: String
    val read: Boolean
    val deleted: Boolean
    val createdAt: Timestamp
    val type: NetworkNotificationTypeEnum
}

data class NetworkNotificationExtend(
    @DocumentId
    override val id: String = "",
    @set:PropertyName("recipient_id")
    @get:PropertyName("recipient_id")
    override var recipientId: String = "",
    @set:PropertyName("type_id")
    @get:PropertyName("type_id")
    override var typeId: String = "",
    override val read: Boolean = false,
    override val deleted: Boolean = false,
    @set:PropertyName("created_at")
    @get:PropertyName("created_at")
    override var createdAt: Timestamp = Timestamp.now(),
    override val type: NetworkNotificationTypeEnum = NetworkNotificationTypeEnum.PROMOTION,
    val data: NetworkNotificationExtendData = NetworkNotificationExtendData(
        NetworkNotificationPayload(NetworkNotificationPayloadData())
    )
) : NetworkNotification

internal fun NetworkNotificationExtend.toDomainModel() =
    NotificationExtend(
        id = id,
        recipientId = recipientId,
        typeId = typeId,
        type = type.toDomainModel(),
        data = data.toDomainModel(),
        read = read,
        deleted = deleted, createdAt = createdAt
    )

data class NetworkNotificationExtendData(
    val payload: NetworkNotificationPayload = NetworkNotificationPayload(
        NetworkNotificationPayloadData()
    )
)

internal fun NetworkNotificationExtendData.toDomainModel() =
    NotificationExtendData(payload = payload.toDomainModel())

data class NetworkNotificationOrderStatus(
    @DocumentId
    override val id: String = "",
    @set:PropertyName("recipient_id")
    @get:PropertyName("recipient_id")
    override var recipientId: String = "",
    @set:PropertyName("type_id")
    @get:PropertyName("type_id")
    override var typeId: String = "",
    override val read: Boolean = false,
    override val deleted: Boolean = false,
    @set:PropertyName("created_at")
    @get:PropertyName("created_at")
    override var createdAt: Timestamp = Timestamp.now(),
    override val type: NetworkNotificationTypeEnum = NetworkNotificationTypeEnum.ORDER_STATUS,
    val data: NetworkNotificationOrderData = NetworkNotificationOrderData(
        NetworkNotificationOrderDataProduct(),
        NetworkNotificationPayload(NetworkNotificationPayloadData())
    )
) : NetworkNotification

internal fun NetworkNotificationOrderStatus.toDomainModel() =
    NotificationOrderStatus(
        id = id,
        recipientId = recipientId,
        typeId = typeId,
        type = type.toDomainModel(),
        data = data.toDomainModel(),
        read = read,
        deleted = deleted, createdAt = createdAt
    )

data class NetworkNotificationOrderData(
    val product: NetworkNotificationOrderDataProduct = NetworkNotificationOrderDataProduct(),
    val payload: NetworkNotificationPayload = NetworkNotificationPayload(
        NetworkNotificationPayloadData()
    )
)

internal fun NetworkNotificationOrderData.toDomainModel() =
    NotificationOrderStatusData(
        product = product.toDomainModel(),
        payload = payload.toDomainModel()
    )

data class NetworkNotificationPayload(
    val notification: NetworkNotificationPayloadData = NetworkNotificationPayloadData()
)

internal fun NetworkNotificationPayload.toDomainModel() =
    NotificationPayload(notification = notification.toDomainModel())

data class NetworkNotificationPayloadData(
    val body: String = "",
    val title: String = "",
    val image: String = ""
)

internal fun NetworkNotificationPayloadData.toDomainModel() =
    NotificationPayloadData(body = body, title = title, image = image)

data class NetworkNotificationOrderDataProduct(
    @set:PropertyName("order_id")
    @get:PropertyName("order_id")
    var orderId: String = "",
    @set:PropertyName("order_item_id")
    @get:PropertyName("order_item_id")
    var orderItemId: String = ""
)

internal fun NetworkNotificationOrderDataProduct.toDomainModel() =
    NotificationOrderDataProduct(orderId = orderId, orderItemId = orderItemId)

/**
 * Overview
 */
@Serializable
data class NetworkNotificationOverview(
    val type: NetworkNotificationType,
    val quantity: Int
)

//internal fun NetworkNotificationOverview.toDatabaseModel() =
//    vn.quanprolazer.fashione.data.database.models.NotificationOverview(
//        quantity = quantity,
//        description = type.description,
//        id = type.id,
//        imageUrl = type.imageUrl,
//        name = type.name.toDomainModel()
//    )

@Serializable
data class NetworkNotificationOverviewResponse(
    val notifications: List<NetworkNotificationOverview>,
    val total: Int
)

internal fun NetworkNotificationOverview.toDomainModel() = NotificationOverview(
    type.toDomainModel(), quantity
)