/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vn.quanprolazer.fashione.data.domain.model.CartItem
import vn.quanprolazer.fashione.databinding.ListItemCartBinding


abstract class SwipeableAdapter<T, VH: RecyclerView.ViewHolder>(diffCallback: DiffUtil.ItemCallback<T>) : ListAdapter<T, VH>(diffCallback) {
    private var removedItems = mutableListOf<T>()

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

class ItemSwipeHandler<T>(
    private val adapter: SwipeableAdapter<T, *>,
    private val onItemRemoved: ((item: T) -> Unit)? = null
): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val item = adapter.removeItem(position) ?: return
        onItemRemoved?.invoke(item)
    }
}

class CartItemAdapter(private val clickListener: CartItemQuantityControlClick) :
    SwipeableAdapter<CartItem, CartItemAdapter.CartItemViewHolder>(CartItemDiffCallback) {

    private var recentlyDeleteItem: CartItem? = null
    private var recentlyDeleteItemPosition: Int? = null


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

    fun deleteItem(position: Int) {
        recentlyDeleteItem = getItem(position)
        recentlyDeleteItemPosition = position
        currentList.removeAt(position)
        notifyItemRemoved(position)
//        showUndoSnackbar()
    }

//    private fun showUndoSnackbar() {
//        val view: View = mActivity.findViewById(R.id.coordinator_layout)
//        val snackbar: Snackbar = Snackbar.make(
//            view, R.string.snack_bar_text, Snackbar.LENGTH_LONG
//        )
//        snackbar.setAction(R.string.snack_bar_undo) { v -> undoDelete() }
//        snackbar.show()
//    }
//
//    private fun undoDelete() {
//        mListItems.add(
//            mRecentlyDeletedItemPosition, mRecentlyDeletedItem
//        )
//        notifyItemInserted(mRecentlyDeletedItemPosition)
//    }

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
