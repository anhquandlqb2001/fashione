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
import vn.quanprolazer.fashione.databinding.ListImageProductBinding
import vn.quanprolazer.fashione.domain.models.ProductImage

class ProductImageAdapter : ListAdapter<ProductImage, ProductImageAdapter.ViewHolder>(
    ProductImageDiffCallback
) {
    class ViewHolder(val binding: ListImageProductBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListImageProductBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(image: ProductImage) {
            binding.image = image
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object ProductImageDiffCallback : DiffUtil.ItemCallback<ProductImage>() {
    override fun areItemsTheSame(oldItem: ProductImage, newItem: ProductImage): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: ProductImage, newItem: ProductImage): Boolean {
        return oldItem == newItem
    }

}
