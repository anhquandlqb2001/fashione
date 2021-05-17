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
import vn.quanprolazer.fashione.databinding.ListItemPickupAddressBinding
import vn.quanprolazer.fashione.domain.models.PickupAddress


class PickupAddressAdapter(private val onPickupAddressListener: OnPickupAddressListener) :
    ListAdapter<PickupAddress, RecyclerView.ViewHolder>(
        PickupAddressDiffUtil
    ) {

    class PickupAddressViewHolder(private val binding: ListItemPickupAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(address: PickupAddress, clickListener: OnPickupAddressListener) {
            binding.address = address
            binding.pickupAddressListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): PickupAddressViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemPickupAddressBinding.inflate(layoutInflater)
                return PickupAddressViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickupAddressViewHolder {
        return PickupAddressViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PickupAddressViewHolder).bind(getItem(position), onPickupAddressListener)
    }
}


object PickupAddressDiffUtil : DiffUtil.ItemCallback<PickupAddress>() {
    override fun areItemsTheSame(oldItem: PickupAddress, newItem: PickupAddress): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PickupAddress, newItem: PickupAddress): Boolean {
        return oldItem == newItem
    }

}

abstract class OnPickupAddressListener() {
    abstract fun onClickUpdateAddress(pickupAddress: PickupAddress)
    abstract fun onClickChoosePickupAddress(pickupAddress: PickupAddress)
}