/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.databinding.ListItemAddressPickupSpinnerBinding
import vn.quanprolazer.fashione.domain.models.BaseAddressPickupImpl


class PickupAddressSpinnerAdapter(
    context: Context,
    resId: Int,
    private var data: Array<BaseAddressPickupImpl>
) : ArrayAdapter<BaseAddressPickupImpl>(context, resId, data) {
    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): BaseAddressPickupImpl {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return data[position].code.toLong()
    }

    fun refreshList(freshData: Array<BaseAddressPickupImpl>) {
        data += freshData
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ListItemAddressPickupSpinnerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.list_item_address_pickup_spinner, parent, false
        )
        binding.address = getItem(position)
        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ListItemAddressPickupSpinnerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.list_item_address_pickup_spinner, parent, false
        )
        binding.address = getItem(position)
        return binding.root
    }
}
