/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */


package vn.quanprolazer.fashione.data.network.services.firestores

import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.models.ReviewStatus

interface OrderService {

    suspend fun updateOrderReviewStatus(
        reviewStatus: ReviewStatus,
        orderItemId: String
    ): Resource<Boolean>

}