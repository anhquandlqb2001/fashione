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
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import vn.quanprolazer.fashione.data.domain.model.CartItem
import vn.quanprolazer.fashione.databinding.ListItemCartBinding

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
