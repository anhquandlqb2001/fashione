/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.utilities.bindings

import android.view.View
import android.widget.Button
import androidx.databinding.BindingAdapter
import vn.quanprolazer.fashione.domain.models.AuthenticationState


@BindingAdapter("shouldDisplayBuyButton")
fun Button.shouldDisplayBuyButton(state: AuthenticationState?) {
    state?.let {
        when (state) {
            AuthenticationState.AUTHENTICATED -> visibility = View.VISIBLE
            else -> visibility = View.GONE
        }
    }
}

@BindingAdapter("addToCartVisible")
fun Button.addToCartVisible(orderQty: Number?) {
    orderQty?.let {
        visibility = if (orderQty.toInt() > 0) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }
}