/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.adapters

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import vn.quanprolazer.fashione.data.domain.model.CartItem
import vn.quanprolazer.fashione.databinding.ListItemCartBinding


abstract class SwipeableAdapter<T, VH : RecyclerView.ViewHolder>(diffCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, VH>(
        diffCallback
    ) {
    open var removedItems = mutableListOf<T>()

    fun removeItem(position: Int): T? {
        if (position >= itemCount) return null
        val item = currentList[position]
        removedItems.add(item)
        val actualList = currentList - removedItems
        if (actualList.isEmpty()) removedItems.clear()
        submit(actualList, true)
        return item
    }

    private fun submit(list: List<T>?, isLocalSubmit: Boolean) {
        if (!isLocalSubmit) removedItems.clear()
        super.submitList(list)
    }

    @CallSuper
    override fun submitList(list: List<T>?) {
        submit(list, false)
        notifyDataSetChanged()
    }
}

class ItemSwipeHandler<T>(private val adapter: SwipeableAdapter<T, *>,
                          private val icon: Drawable? = null,
                          private val background: ColorDrawable = ColorDrawable(Color.RED),
                          private val onItemRemoved: ((position: Int, item: T) -> Unit)? = null,
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    override fun onMove(recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
    ): Boolean = false


    private var removedItem: T? = null
    private var removedItemPosition: Int? = null

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val item = adapter.removeItem(position) ?: return

        removedItem = item
        removedItemPosition = position

        onItemRemoved?.invoke(position, item)
    }


    override fun onChildDraw(c: Canvas,
                             recyclerView: RecyclerView,
                             viewHolder: RecyclerView.ViewHolder,
                             dX: Float,
                             dY: Float,
                             actionState: Int,
                             isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView: View = viewHolder.itemView
        val backgroundCornerOffset = 20

        val iconMargin = (itemView.height - icon!!.intrinsicHeight) / 2
        val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
        val iconBottom = iconTop + icon.intrinsicHeight
        Timber.i(dX.toString())
        when { // Swiping to the right
            (dX < 0) -> { // Swiping to the left
                val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
                val iconRight = itemView.right - iconMargin

                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

                background.setBounds(
                    itemView.right + dX.toInt() - backgroundCornerOffset,
                    itemView.top,
                    itemView.right,
                    itemView.bottom
                )
            }
            else -> { // view is unSwiped
                background.setBounds(0, 0, 0, 0)
                icon.setBounds(0, 0, 0, 0)
            }
        }
        background.draw(c)
        if (dX < -250) {
            icon.draw(c)
        }
    }
}

class CartItemAdapter(private val clickListener: CartItemQuantityControlClick) :
    SwipeableAdapter<CartItem, CartItemAdapter.CartItemViewHolder>(CartItemDiffCallback) {

    class CartItemViewHolder(private val binding: ListItemCartBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {

        companion object {
            fun from(parent: ViewGroup): CartItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemCartBinding.inflate(layoutInflater)
                return CartItemViewHolder(binding)
            }
        }

        fun bind(cartItem: CartItem, clickListener: CartItemQuantityControlClick) {
            binding.cbBuy.setOnCheckedChangeListener(null)
            binding.cartItem = cartItem
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        return CartItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }


}

class CartItemQuantityControlClick(val quantityControlClickListener: (cartItem: CartItem, value: Int) -> Unit
) {
    fun quantityControlClick(cartItem: CartItem, value: Int) = quantityControlClickListener(
        cartItem, value
    )
}

object CartItemDiffCallback : DiffUtil.ItemCallback<CartItem>() {
    override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem == newItem
    }
}
