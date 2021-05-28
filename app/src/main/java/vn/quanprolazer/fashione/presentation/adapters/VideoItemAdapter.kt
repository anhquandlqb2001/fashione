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
import vn.quanprolazer.fashione.databinding.ListItemVideoBinding
import vn.quanprolazer.fashione.domain.models.LiveVideo

class VideoItemAdapter(private val listener: VideoItemListener) :
    ListAdapter<LiveVideo, VideoItemAdapter.VideoViewHolder>(VideoDiff) {
    class VideoViewHolder(private val binding: ListItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(video: LiveVideo, listener: VideoItemListener) {
            binding.video = video
            binding.listener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): VideoViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemVideoBinding.inflate(layoutInflater)
                return VideoViewHolder(binding)
            }
        }

    }

    object VideoDiff : DiffUtil.ItemCallback<LiveVideo>() {
        override fun areItemsTheSame(oldItem: LiveVideo, newItem: LiveVideo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: LiveVideo, newItem: LiveVideo): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }
}

abstract class VideoItemListener {
    abstract fun onClick(uri: String)
}
