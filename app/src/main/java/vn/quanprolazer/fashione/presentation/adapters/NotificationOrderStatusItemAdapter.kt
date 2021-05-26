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
import vn.quanprolazer.fashione.databinding.ListItemNotificationOrderStatusBinding
import vn.quanprolazer.fashione.domain.models.NotificationOrderStatus


class NotificationItemAdapter :
    ListAdapter<NotificationOrderStatus, NotificationItemAdapter.NotificationOrderStatusViewHolder>(
        DiffCallback
    ) {

    class NotificationOrderStatusViewHolder private constructor(val binding: ListItemNotificationOrderStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NotificationOrderStatus) {
            binding.notification = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): NotificationOrderStatusViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ListItemNotificationOrderStatusBinding.inflate(layoutInflater, parent, false)
                return NotificationOrderStatusViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationOrderStatusViewHolder {
        return NotificationOrderStatusViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NotificationOrderStatusViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object DiffCallback : DiffUtil.ItemCallback<NotificationOrderStatus>() {
        override fun areItemsTheSame(
            oldItem: NotificationOrderStatus,
            newItem: NotificationOrderStatus
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: NotificationOrderStatus,
            newItem: NotificationOrderStatus
        ): Boolean {
            return oldItem == newItem
        }
    }
}