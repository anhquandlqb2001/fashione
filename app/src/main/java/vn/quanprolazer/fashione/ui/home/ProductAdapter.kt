package vn.quanprolazer.fashione.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vn.quanprolazer.fashione.databinding.ListItemProductBinding


data class HomeProductInfo(val id: Long, val image: String, val name: String, val price: String)

class ProductAdapter(val productList: List<HomeProductInfo>) : ListAdapter<HomeProductInfo, RecyclerView.ViewHolder>(ProductDiffUtil()) {

    class ProductViewHolder(private val binding: ListItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(productInfo: HomeProductInfo) {
            binding.product = productInfo
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : ProductViewHolder {
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
        (holder as ProductViewHolder).bind(productList[position])
    }
}


class ProductDiffUtil : DiffUtil.ItemCallback<HomeProductInfo>() {
    override fun areItemsTheSame(oldItem: HomeProductInfo, newItem: HomeProductInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: HomeProductInfo, newItem: HomeProductInfo): Boolean {
        return oldItem == newItem
    }

}