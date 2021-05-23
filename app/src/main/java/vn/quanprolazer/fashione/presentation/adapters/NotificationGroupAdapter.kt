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
import vn.quanprolazer.fashione.domain.models.NotificationOverview
import vn.quanprolazer.fashione.domain.models.NotificationTypeEnum

class NotificationGroupAdapter :
    ListAdapter<NotificationOverview, NotificationGroupAdapter.NotificationGroupViewHolder>(
        NotificationGroupDiffUtil
    ) {

    class NotificationGroupViewHolder(private val binding: ListItemNotificationGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val ignoreNotificationGroups = listOf(NotificationTypeEnum.ORDER_STATUS)

        companion object {
            fun from(parent: ViewGroup): NotificationGroupViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemNotificationGroupBinding.inflate(layoutInflater)
                return NotificationGroupViewHolder(binding)
            }
        }

        fun bind(notificationOverview: NotificationOverview) {
            if (notificationOverview.type.name  !in ignoreNotificationGroups) {
                binding.notificationOverview = notificationOverview
                binding.executePendingBindings()
            }
        }
    }

    override fun getItemCount() = currentList.size - 1

    object NotificationGroupDiffUtil : DiffUtil.ItemCallback<NotificationOverview>() {
        override fun areItemsTheSame(
            oldItem: NotificationOverview,
            newItem: NotificationOverview
        ): Boolean {
            return oldItem.type.id == newItem.type.id
        }

        override fun areContentsTheSame(
            oldItem: NotificationOverview,
            newItem: NotificationOverview
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