/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.utilities.bindings

import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.domain.models.OrderItemStatusType
import vn.quanprolazer.fashione.domain.models.ReviewStatus


@BindingAdapter("purchaseReviewStatus")
fun LinearLayout.purchaseReviewStatus(status: ReviewStatus?) {
    status?.let {
        visibility = when (status) {
            ReviewStatus.YES -> View.GONE
            ReviewStatus.NO -> View.VISIBLE
        }
    }
}


@BindingAdapter("purchaseDelivered")
fun RelativeLayout.purchaseDelivered(status: OrderItemStatusType?) {
    status?.let {
        visibility =
            if (status == OrderItemStatusType.DELIVERED || status == OrderItemStatusType.COMPLETE) {
                View.VISIBLE
            } else {
                View.GONE
            }
    }
}

@BindingAdapter("watched")
fun RelativeLayout.watched(watched: Boolean?) {
    watched?.let {
        if (watched) foreground = (ContextCompat.getDrawable(context, R.color.notification_watched))
        else null
    }
}

@BindingAdapter("shouldDisplayBottomCheckout")
fun ConstraintLayout.shouldDisplayBottomCheckout(bool: LiveData<Boolean>?) {
    bool?.value.let {
        visibility = if (bool?.value == true) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}