package vn.quanprolazer.fashione

import android.widget.TextView
import androidx.databinding.BindingAdapter

@ExperimentalStdlibApi
@BindingAdapter("categoryName")
fun setCategoryName(view: TextView, categoryName: String?) {
    categoryName?.let {
        view.text = categoryName.uppercase()
    }
}