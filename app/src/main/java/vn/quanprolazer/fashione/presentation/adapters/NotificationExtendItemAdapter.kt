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
import vn.quanprolazer.fashione.databinding.ListItemNotificationExtendBinding
import vn.quanprolazer.fashione.domain.models.NotificationExtend


class NotificationExtendItemAdapter :
    ListAdapter<NotificationExtend, NotificationExtendItemAdapter.NotificationExtendViewHolder>(
        DiffCallback
    ) {

    class NotificationExtendViewHolder private constructor(val binding: ListItemNotificationExtendBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NotificationExtend) {
            binding.notification = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): NotificationExtendViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ListItemNotificationExtendBinding.inflate(layoutInflater, parent, false)
                return NotificationExtendViewHolder(binding)
            }
        }
    }


    object DiffCallback : DiffUtil.ItemCallback<NotificationExtend>() {
        override fun areItemsTheSame(
            oldItem: NotificationExtend,
            newItem: NotificationExtend
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: NotificationExtend,
            newItem: NotificationExtend
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationExtendViewHolder {
        return NotificationExtendViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NotificationExtendViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}