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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.quanprolazer.fashione.databinding.ListItemMessageIncomingBinding
import vn.quanprolazer.fashione.databinding.ListItemMessageOutgoingBinding
import vn.quanprolazer.fashione.domain.models.Message
import vn.quanprolazer.fashione.domain.models.MessageDirect

class MessageAdapter : ListAdapter<DataItem, RecyclerView.ViewHolder>(MessageDiffCallback) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    companion object {
        private const val ITEM_VIEW_TYPE_INCOMING = 0
        private const val ITEM_VIEW_TYPE_OUTGOING = 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is IncomingMessageViewHolder -> {
                val message = getItem(position) as DataItem.IncomingMessage
                holder.bind(message.message)
            }
            is OutgoingMessageViewHolder -> {
                val message = getItem(position) as DataItem.OutgoingMessage
                holder.bind(message.message)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_INCOMING -> IncomingMessageViewHolder.from(parent)
            ITEM_VIEW_TYPE_OUTGOING -> OutgoingMessageViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    fun bindMessages(list: List<Message>?) {
        adapterScope.launch {
            val items = mutableListOf<DataItem>()
            list?.map { message ->
                when (message.direction) {
                    MessageDirect.INCOMING -> items.add(DataItem.IncomingMessage(message))
                    MessageDirect.OUTGOING -> items.add(DataItem.OutgoingMessage(message))
                }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    fun updateNewIncomingMessage(message: Message) {
        adapterScope.launch {
            val item = DataItem.IncomingMessage(message)
            val items = mutableListOf<DataItem>()
            items.addAll(currentList)
            if (items.find { it.id == item.id } == null) {
                items.add(item)
            }
            withContext(Dispatchers.Default) {
                submitList(items)
            }
        }
    }

    fun updateNewOutgoingMessage(message: Message) {
        adapterScope.launch {
            val item = DataItem.OutgoingMessage(message)
            val items: MutableList<DataItem> = mutableListOf()
            items.addAll(currentList)
            items.add(item)
            withContext(Dispatchers.Default) {
                submitList(items)
            }
        }
    }

    override fun submitList(list: MutableList<DataItem>?) {
        super.submitList(list)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.IncomingMessage -> ITEM_VIEW_TYPE_INCOMING
            is DataItem.OutgoingMessage -> ITEM_VIEW_TYPE_OUTGOING
        }
    }

    class IncomingMessageViewHolder(val binding: ListItemMessageIncomingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Message) {
            binding.message = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): IncomingMessageViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemMessageIncomingBinding.inflate(layoutInflater, parent, false)
                return IncomingMessageViewHolder(binding)
            }
        }
    }

    class OutgoingMessageViewHolder(val binding: ListItemMessageOutgoingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Message) {
            binding.message = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): OutgoingMessageViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = ListItemMessageOutgoingBinding.inflate(inflater, parent, false)
                return OutgoingMessageViewHolder(view)
            }
        }
    }
}


object MessageDiffCallback : DiffUtil.ItemCallback<DataItem>() {

    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }


}

sealed class DataItem {

    abstract val id: String

    data class IncomingMessage(val message: Message) : DataItem() {
        override val id = message.id
    }

    data class OutgoingMessage(val message: Message) : DataItem() {
        override val id = message.id
    }


}