/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vn.quanprolazer.fashione.data.domain.model.CheckoutItem
import vn.quanprolazer.fashione.databinding.ListItemCheckoutBinding

class CheckoutViewHolder(private val binding: ListItemCheckoutBinding) : RecyclerView.ViewHolder(binding.root){
    companion object {
        fun from(parent: ViewGroup): CheckoutViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemCheckoutBinding.inflate(layoutInflater)
            return CheckoutViewHolder(binding)
        }
    }

    fun bind(item: CheckoutItem) {
        binding.checkoutItem = item
        binding.executePendingBindings()
    }
}

class CheckoutItemAdapter : ListAdapter<CheckoutItem, CheckoutViewHolder>(DiffCallback) {
    object DiffCallback : DiffUtil.ItemCallback<CheckoutItem>() {
        override fun areItemsTheSame(oldItem: CheckoutItem, newItem: CheckoutItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CheckoutItem, newItem: CheckoutItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutViewHolder {
        return CheckoutViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CheckoutViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}