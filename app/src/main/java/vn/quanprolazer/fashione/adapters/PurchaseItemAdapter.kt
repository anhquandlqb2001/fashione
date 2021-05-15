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
import vn.quanprolazer.fashione.data.domain.model.Purchase
import vn.quanprolazer.fashione.databinding.ListItemPurchaseBinding

class PurchaseItemAdapter(private val purchaseItemListener: PurchaseItemListener) :
    ListAdapter<Purchase, PurchaseItemAdapter.ViewHolder>(PurchaseItemDiffCallback) {

    class ViewHolder(val binding: ListItemPurchaseBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemPurchaseBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(purchase: Purchase, purchaseItemListener: PurchaseItemListener) {
            binding.purchaseItem = purchase
            binding.purchaseItemListener = purchaseItemListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), purchaseItemListener)
    }

    override fun submitList(list: MutableList<Purchase>?) {
        super.submitList(list)
        notifyDataSetChanged()
    }

    object PurchaseItemDiffCallback : DiffUtil.ItemCallback<Purchase>() {
        override fun areItemsTheSame(oldItem: Purchase, newItem: Purchase): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Purchase, newItem: Purchase): Boolean {
            return oldItem == newItem
        }

    }

}

abstract class PurchaseItemListener {
    abstract fun onClick(purchase: Purchase)
    abstract fun onClickAddReview(purchase: Purchase)
}