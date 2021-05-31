/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.utilities.bindings

import android.widget.RatingBar
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData

@BindingAdapter("rating")
fun RatingBar.rating(rating: MutableLiveData<String>?) {
    rating?.let {
        rating.value = getRating().toInt().toString()
    }
}