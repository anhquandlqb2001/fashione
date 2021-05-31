/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.utilities.bindings

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import vn.quanprolazer.fashione.domain.models.CartItem
import vn.quanprolazer.fashione.domain.models.NotificationOverviewResponse
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.presentation.adapters.CartItemAdapter
import vn.quanprolazer.fashione.presentation.adapters.NotificationGroupAdapter


@BindingAdapter("cartItems")
fun RecyclerView.setCartItems(cartItems: LiveData<Resource<List<CartItem>>>?) {
    cartItems?.let {
        when (it.value) {
            is Resource.Success -> {
                visibility = View.VISIBLE
                (this.adapter as CartItemAdapter).submitList((it.value as Resource.Success<List<CartItem>>).data.toMutableList())
            }
            else -> {
            }
        }
    }
}


@BindingAdapter("notificationOverviewResponse")
fun RecyclerView.notificationOverviewResponse(notificationOverviewResponse: NotificationOverviewResponse?) {
    notificationOverviewResponse?.let {
        (this.adapter as NotificationGroupAdapter).submitList(notificationOverviewResponse.notifications)
    }
}