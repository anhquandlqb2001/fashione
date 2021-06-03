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

class NotificationGroupAdapter(private val listener: NotificationGroupItemListener) :
    ListAdapter<NotificationOverview, NotificationGroupAdapter.NotificationGroupViewHolder>(
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

        fun bind(
            notificationOverview: NotificationOverview,
            listener: NotificationGroupItemListener
        ) {
            binding.notificationOverview = notificationOverview
            binding.listener = listener
            binding.executePendingBindings()

        }
    }

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
        val item = getItem(position)
        holder.bind(item, listener)
    }

}

abstract class NotificationGroupItemListener {
    abstract fun onClick(typeId: String)
}