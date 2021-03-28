package vn.quanprolazer.fashione.ui.home

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.databinding.FragmentHomeBinding
import vn.quanprolazer.fashione.databinding.ListItemCategoryBinding
import vn.quanprolazer.fashione.domain.Category

//data class CategoryButtonBackground(val startColor: Int, val endColor: Int)
//
//data class CategoryInfo(val text: String, val backgroundColor: CategoryButtonBackground)


class CategoryAdapter(private val clickListener: OnClickCategoryListener) : ListAdapter<Category, CategoryAdapter.CategoryItemViewHolder>(DiffCallback) {

    class CategoryItemViewHolder(private val binding: ListItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): CategoryItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemCategoryBinding.inflate(layoutInflater)
                return CategoryItemViewHolder(binding)
            }
        }

        fun bind(category: Category, clickListener: OnClickCategoryListener) {
            binding.category = category
            binding.clickListener = clickListener
//            val backgroundDrawable = binding.ivCategory.background as GradientDrawable
//            backgroundDrawable.run {
//                mutate()
//                colors = intArrayOf(categoryInfo.backgroundColor.startColor.toInt(), categoryInfo.backgroundColor.endColor.toInt())
//            }
            binding.executePendingBindings()
        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        return CategoryItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }
}

object DiffCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.categoryId == newItem.categoryId
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }

}

class OnClickCategoryListener(val clickListener: (category: Category) -> Unit) {
    fun onClick(category: Category) = clickListener(category)
}