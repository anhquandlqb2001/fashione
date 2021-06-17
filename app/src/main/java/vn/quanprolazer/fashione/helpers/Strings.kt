/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.helpers

import androidx.annotation.StringRes
import vn.quanprolazer.fashione.FashioneApplication

object Strings {
    fun get(@StringRes stringRes: Int, vararg formatArgs: Any = emptyArray()): String {
        return FashioneApplication.instance.getString(stringRes, *formatArgs)
    }
}