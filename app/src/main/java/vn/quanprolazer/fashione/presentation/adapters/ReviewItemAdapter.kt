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
import vn.quanprolazer.fashione.databinding.ListItemReviewBinding
import vn.quanprolazer.fashione.domain.models.ReviewRetrofit

class ReviewItemAdapter :
    ListAdapter<ReviewRetrofit, ReviewItemAdapter.ViewHolder>(ReviewItemDiffCallback) {

    class ViewHolder(val binding: ListItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemReviewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(review: ReviewRetrofit) {
            binding.review = review
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: MutableList<ReviewRetrofit>?) {
        super.submitList(list)
        notifyDataSetChanged()
    }

    object ReviewItemDiffCallback : DiffUtil.ItemCallback<ReviewRetrofit>() {
        override fun areItemsTheSame(
            oldItem: ReviewRetrofit,
            newItem: ReviewRetrofit
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ReviewRetrofit,
            newItem: ReviewRetrofit
        ): Boolean {
            return oldItem == newItem
        }

    }
}