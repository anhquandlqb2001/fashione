/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import vn.quanprolazer.fashione.data.domain.model.CartItem
import vn.quanprolazer.fashione.databinding.ListItemCartBinding


class CartItemAdapter(private val clickListener: CartItemListener) :
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

        fun bind(cartItem: CartItem, clickListener: CartItemListener) {
            binding.cbBuy.setOnCheckedChangeListener(null)
            binding.cartItem = cartItem
            binding.clickListener = clickListener

            //in some cases, it will prevent unwanted situations
            binding.cbBuy.setOnCheckedChangeListener(null)

            //if true, your checkbox will be selected, else unselected
            binding.cbBuy.isChecked = cartItem.isChecked

            binding.cbBuy.setOnCheckedChangeListener { _, isChecked -> //set your object's last status
                cartItem.isChecked = isChecked
            }

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

abstract class CartItemListener() {
    abstract fun quantityControlClick(cartItem: CartItem, value: Int)
    abstract fun checkBoxClick(cartItem: CartItem)
}

//= quantityControlClickListener(
//cartItem, value
//)

object CartItemDiffCallback : DiffUtil.ItemCallback<CartItem>() {
    override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem == newItem
    }
}
