/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vn.quanprolazer.fashione.databinding.ListItemProductBinding
import vn.quanprolazer.fashione.domain.models.Product


class ProductAdapter(private val clickListener: OnClickListener) :
    ListAdapter<Product, RecyclerView.ViewHolder>(
        ProductDiffUtil()
    ) {

    class ProductViewHolder(private val binding: ListItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, clickListener: OnClickListener) {
            binding.product = product
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ProductViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemProductBinding.inflate(layoutInflater)
                return ProductViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProductViewHolder).bind(getItem(position), clickListener)
    }
}


class ProductDiffUtil : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

}

class OnClickListener(val clickListener: (product: Product) -> Unit) {
    fun onClick(product: Product) = clickListener(product)
}