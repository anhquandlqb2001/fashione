/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.utilities

import android.graphics.Rect
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Timestamp
import org.json.JSONArray
import org.json.JSONObject
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private val PUNCTUATION = listOf(", ", "; ", ": ", " ")

const val PRODUCT_NAME_LIMIT = 10


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
 * Linearlayout vertical margin space
 */
class SpacesItemDecoration(private val space: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) < 1) {
            outRect.top = space
        }
        outRect.bottom = space
    }
}

/**
 * Function to convert raw price string to formatted price follow currency
 */
fun convertPriceStringToCurrencyString(text: String, currencyCode: String = "VND"): String? {
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance(currencyCode)
    return format.format(text.toFloat())
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

/**
 * Extension func to map and change array input.
 */
inline fun <T> MutableList<T>.mapInPlace(mutator: (T) -> T) {
    val iterate = this.listIterator()
    while (iterate.hasNext()) {
        val oldValue = iterate.next()
        val newValue = mutator(oldValue)
        if (newValue !== oldValue) {
            iterate.set(newValue)
        }
    }
}


fun <T> MutableLiveData<MutableList<T>>.addNewItem(item: T) {
    val oldValue = this.value ?: mutableListOf()
    oldValue.add(item)
    this.value = oldValue
}

fun <T> MutableLiveData<T>.notifyUpdate() {
    this.value = this.value
}

fun <T> MutableLiveData<MutableList<T>>.addNewItemAt(index: Int, item: T) {
    val oldValue = this.value ?: mutableListOf()
    oldValue.add(index, item)
    this.value = oldValue
}

fun <T> MutableLiveData<MutableList<T>>.removeItemAt(index: Int) {
    if (!this.value.isNullOrEmpty()) {
        val oldValue = this.value
        oldValue?.removeAt(index)
        this.value = oldValue
    } else {
        this.value = mutableListOf()
    }
}

fun fromTimestamp(timestamp: Timestamp, pattern: String = "dd/MM/yyyy HH:mm"): String {
    val sdf = SimpleDateFormat(pattern, Locale.CHINESE)
    return sdf.format(timestamp.toDate())
}