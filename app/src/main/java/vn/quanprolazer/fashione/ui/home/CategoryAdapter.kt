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

data class CategoryButtonBackground(val startColor: Int, val endColor: Int)

data class CategoryInfo(val text: String, val backgroundColor: CategoryButtonBackground)

val categories = listOf(
    CategoryInfo("Nữ", CategoryButtonBackground(Color.argb(100, 102, 126, 234), Color.argb(100, 100, 182, 255))),
    CategoryInfo("Nam", CategoryButtonBackground(Color.parseColor("#FF5858"), Color.parseColor("#FB5895"))),
    CategoryInfo("Trẻ em", CategoryButtonBackground(Color.parseColor("#43E97B"), Color.parseColor("#38F9D7")))
)

class CategoryAdapter : ListAdapter<Category, CategoryAdapter.CategoryItemViewHolder>(DiffCallback) {

    class CategoryItemViewHolder(private val binding: ListItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): CategoryItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemCategoryBinding.inflate(layoutInflater)
                return CategoryItemViewHolder(binding)
            }
        }

        fun bind(category: Category) {
            binding.category = category
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
        holder.bind(getItem(position))
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
