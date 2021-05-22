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
import vn.quanprolazer.fashione.databinding.ListItemNotificationGroupBinding
import vn.quanprolazer.fashione.domain.models.NotificationType

class NotificationGroupAdapter :
    ListAdapter<NotificationType, NotificationGroupAdapter.NotificationGroupViewHolder>(
        NotificationGroupDiffUtil
    ) {

    class NotificationGroupViewHolder(private val binding: ListItemNotificationGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): NotificationGroupViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemNotificationGroupBinding.inflate(layoutInflater)
                return NotificationGroupViewHolder(binding)
            }
        }

        fun bind(notificationType: NotificationType) {
            binding.notificationType = notificationType
            binding.executePendingBindings()
        }
    }

    object NotificationGroupDiffUtil : DiffUtil.ItemCallback<NotificationType>() {
        override fun areItemsTheSame(
            oldItem: NotificationType,
            newItem: NotificationType
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: NotificationType,
            newItem: NotificationType
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationGroupViewHolder {
        return NotificationGroupViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NotificationGroupViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}