/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.utilities

import android.app.Activity
import android.app.Dialog
import android.view.Window
import android.widget.ImageView
import com.bumptech.glide.Glide
import vn.quanprolazer.fashione.R
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade

class ViewDialog(val activity: Activity) {
    var dialog: Dialog? = null
    fun showDialog() {
        if (dialog == null) {
            dialog = Dialog(activity)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.setCancelable(false)
            dialog!!.setContentView(R.layout.fragment_loading)

            val gifImageView: ImageView = dialog!!.findViewById(R.id.custom_loading_imageView)

            Glide.with(activity).load(R.drawable.loading).placeholder(R.drawable.loading)
                .centerCrop()
                .transition(withCrossFade()).into(gifImageView)
            dialog!!.show()
        }
    }

    //..also create a method which will hide the dialog when some work is done
    fun hideDialog() {
        if (dialog != null) {
            dialog!!.cancel()
        }
    }
}