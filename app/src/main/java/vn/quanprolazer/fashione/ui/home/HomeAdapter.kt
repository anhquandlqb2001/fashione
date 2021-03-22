package vn.quanprolazer.fashione.ui.home

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import vn.quanprolazer.fashione.databinding.FragmentHomeBinding
import vn.quanprolazer.fashione.databinding.ListItemCategoryBinding

data class CategoryButtonBackground(val startColor: Int, val endColor: Int)

data class CategoryInfo(val text: String, val backgroundColor: CategoryButtonBackground)


val categories = listOf(
    CategoryInfo("Nữ", CategoryButtonBackground(Color.argb(100, 102, 126, 234), Color.argb(100, 100, 182, 255))),
    CategoryInfo("Nam", CategoryButtonBackground(Color.parseColor("#FF5858"), Color.parseColor("#FB5895"))),
    CategoryInfo("Trẻ em", CategoryButtonBackground(Color.parseColor("#43E97B"), Color.parseColor("#38F9D7")))
)

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ListItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemCategoryBinding.inflate(layoutInflater)
                return ViewHolder(binding)
            }
        }

        fun bind(categoryInfo: CategoryInfo) {
            binding.category = categoryInfo
            val backgroundDrawable = binding.ivCategory.background as GradientDrawable
            backgroundDrawable.run {
                mutate()
                colors = intArrayOf(categoryInfo.backgroundColor.startColor.toInt(), categoryInfo.backgroundColor.endColor.toInt())
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount() = categories.size
}