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
import vn.quanprolazer.fashione.data.domain.model.ReviewWithRating
import vn.quanprolazer.fashione.databinding.ListItemReviewBinding

class ReviewItemAdapter :
    ListAdapter<ReviewWithRating, ReviewItemAdapter.ViewHolder>(ReviewItemDiffCallback) {

    class ViewHolder(val binding: ListItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemReviewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(reviewWithRating: ReviewWithRating) {
            binding.review = reviewWithRating
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object ReviewItemDiffCallback : DiffUtil.ItemCallback<ReviewWithRating>() {
        override fun areItemsTheSame(
            oldItem: ReviewWithRating,
            newItem: ReviewWithRating
        ): Boolean {
            return oldItem.review.id == newItem.review.id
        }

        override fun areContentsTheSame(
            oldItem: ReviewWithRating,
            newItem: ReviewWithRating
        ): Boolean {
            return oldItem == newItem
        }

    }
}