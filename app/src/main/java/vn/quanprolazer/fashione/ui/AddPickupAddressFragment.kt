/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.adapters.PickupAddressSpinnerAdapter
import vn.quanprolazer.fashione.data.domain.model.BaseAddressPickupImpl
import vn.quanprolazer.fashione.databinding.FragmentAddPickupAddressBinding
import vn.quanprolazer.fashione.viewmodels.AddPickupAddressViewModel


@AndroidEntryPoint
class AddPickupAddressFragment : Fragment() {

    private var _binding: FragmentAddPickupAddressBinding? = null

    private val binding: FragmentAddPickupAddressBinding get() = _binding!!

    private val viewModel: AddPickupAddressViewModel by viewModels()

    private val provinceOrCityAdapter: PickupAddressSpinnerAdapter by lazy {
        PickupAddressSpinnerAdapter(
            requireContext(),
            R.layout.list_item_address_pickup_spinner,
            listOf(BaseAddressPickupImpl("-1", "--Nhap tp--")).toTypedArray()
        )
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View { // Inflate the layout for this fragment
        _binding = FragmentAddPickupAddressBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setProvincesOrCitiesData(requireContext())



        viewModel.provinceOrCitySpinnerRows.observe(viewLifecycleOwner, {
            it?.let {
                Timber.i(it.toString())
                provinceOrCityAdapter.refreshList(it.toTypedArray())
            }
        })

        binding.sProvinceOrCity.adapter = provinceOrCityAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

