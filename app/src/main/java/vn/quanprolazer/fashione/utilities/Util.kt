/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.utilities

import android.graphics.Rect
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONArray
import org.json.JSONObject
import java.text.NumberFormat
import java.util.*

private val PUNCTUATION = listOf(", ", "; ", ": ", " ")

/**
 * Truncate long text with a preference for word boundaries and without trailing punctuation.
 */
fun String.smartTruncate(length: Int): String {
    val words = split(" ")
    var added = 0
    var hasMore = false
    val builder = StringBuilder()
    for (word in words) {
        if (builder.length > length) {
            hasMore = true
            break
        }
        builder.append(word)
        builder.append(" ")
        added += 1
    }

    PUNCTUATION.map {
        if (builder.endsWith(it)) {
            builder.replace(builder.length - it.length, builder.length, "")
        }
    }

    if (hasMore) {
        builder.append("...")
    }
    return builder.toString()
}


fun TextInputEditText.onDone(callback: (() -> Unit)?) {
    setOnEditorActionListener { _, actionId, _ ->
        callback?.let {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                callback.invoke()
            }
        }
        false
    }
}


/**
 * GridLayout
 */
class MarginItemDecoration(
    private val spaceSize: Int,
    private val spanCount: Int = 1,
    private val orientation: Int = GridLayoutManager.VERTICAL
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            if (orientation == GridLayoutManager.VERTICAL) {
                if (parent.getChildAdapterPosition(view) < spanCount) {
                    bottom = spaceSize
                }
                if (parent.getChildAdapterPosition(view) % spanCount == 0) {
                    right = spaceSize
                }
            } else {
                if (parent.getChildAdapterPosition(view) < spanCount) {
                    right = spaceSize
                }
                if (parent.getChildAdapterPosition(view) % spanCount == 0) {
                    bottom = spaceSize
                }
            }

            top = spaceSize
            left = spaceSize
        }
    }
}

/**
 *  LinearLayout single line horizontal
 */
class SpacesItemDecoration(private val space: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.left = space
        outRect.right = space
    }
}

/**
 * Function to convert raw price string to formatted price follow currency
 */
fun convertPriceStringToCurrencyString(text: String, currencyCode: String = "VND"): String? {
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance(currencyCode)
    return format.format(text.toInt())
}

/**
 * Extension function to convert JSONObject to Map
 *
 */
fun JSONObject.toMap(): Map<String, *> = keys().asSequence().associateWith {
    when (val value = this[it]) {
        is JSONArray -> {
            val map = (0 until value.length()).associate { Pair(it.toString(), value[it]) }
            JSONObject(map).toMap().values.toList()
        }
        is JSONObject -> value.toMap()
        JSONObject.NULL -> null
        else -> value
    }
}