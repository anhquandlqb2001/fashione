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
        visibility = when (state) {
            AuthenticationState.AUTHENTICATED -> View.VISIBLE
            else -> View.GONE
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

@BindingAdapter("shouldDisplayDecreaseQtyButton")
fun Button.shouldDisplayDecreaseQtyButton(cartItemQty: Int?) {
    cartItemQty?.let {
        isEnabled = cartItemQty > 1
    }
}