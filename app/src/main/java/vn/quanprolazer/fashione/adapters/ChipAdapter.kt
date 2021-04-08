/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

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
import vn.quanprolazer.fashione.databinding.ListItemChipBinding
import vn.quanprolazer.fashione.domain.model.ProductVariant


class ChipAdapter(val clickListener: OnClickChipListener) : ListAdapter<ProductVariant, ChipAdapter.ViewHolder>(ChipDiffCallBack) {

    class ViewHolder(private val binding: ListItemChipBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemChipBinding.inflate(layoutInflater)
                return ViewHolder(binding)
            }
        }

        fun bind(variant: ProductVariant, clickListener: OnClickChipListener) {
            binding.variant = variant
//            val backgroundDrawable = binding.ivCategory.background as GradientDrawable
//            backgroundDrawable.run {
//                mutate()
//                colors = intArrayOf(categoryInfo.backgroundColor.startColor.toInt(), categoryInfo.backgroundColor.endColor.toInt())
//            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }
}

object ChipDiffCallBack : DiffUtil.ItemCallback<ProductVariant>() {
    override fun areItemsTheSame(oldItem: ProductVariant, newItem: ProductVariant): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: ProductVariant, newItem: ProductVariant): Boolean {
        return oldItem == newItem
    }

}

class OnClickChipListener(val clickListener: (variant: ProductVariant) -> Unit) {
    fun onClick(variant: ProductVariant) = clickListener
}