/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.databinding.FragmentAddPickupAddressBinding
import vn.quanprolazer.fashione.domain.models.BaseAddressPickupImpl
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.presentation.adapters.PickupAddressSpinnerAdapter
import vn.quanprolazer.fashione.presentation.utilities.LoadingDialog
import vn.quanprolazer.fashione.presentation.viewmodels.AddPickupAddressViewModel
import vn.quanprolazer.fashione.presentation.viewmodels.CheckoutSharedViewModel


@AndroidEntryPoint
class AddPickupAddressFragment : Fragment() {

    private var _binding: FragmentAddPickupAddressBinding? = null

    private val binding: FragmentAddPickupAddressBinding get() = _binding!!

    private val viewModel: AddPickupAddressViewModel by viewModels()

    private val checkoutSharedViewModel: CheckoutSharedViewModel by activityViewModels()

    private val provinceOrCityAdapter: PickupAddressSpinnerAdapter by lazy {
        PickupAddressSpinnerAdapter(
            requireContext(),
            R.layout.list_item_address_pickup_spinner,
            listOf(BaseAddressPickupImpl("-1", "", "--Nhập tỉnh / thành phố--")).toTypedArray()
        )
    }

    private val districtOrTownAdapter: PickupAddressSpinnerAdapter by lazy {
        PickupAddressSpinnerAdapter(
            requireContext(),
            R.layout.list_item_address_pickup_spinner,
            listOf(BaseAddressPickupImpl("-1", "", "--Nhập quận / huyện--")).toTypedArray()
        )
    }

    private val subdistrictOrVillageAdapter: PickupAddressSpinnerAdapter by lazy {
        PickupAddressSpinnerAdapter(
            requireContext(),
            R.layout.list_item_address_pickup_spinner,
            listOf(BaseAddressPickupImpl("-1", "", "--Nhập phuờng / xã--")).toTypedArray()
        )
    }

    private val loadingDialog: LoadingDialog by lazy { LoadingDialog(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater,
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

        setupProvinceOrCitySpinner()
        setupDistrictOrTownSpinner()
        setupSubdistrictOrTownSpinner()

        viewModel.onSavePickupAddress.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        checkoutSharedViewModel.updateAddressPickup(it.data)
                        Handler(Looper.getMainLooper()).postDelayed({
                            loadingDialog.hideDialog()
                            this.findNavController().popBackStack(R.id.checkoutFragment, false)
                        }, 800)
                    }
                    is Resource.Loading -> {
                        loadingDialog.showDialog()
                        binding.btnSave.isEnabled = false
                    }
                    is Resource.Error -> {
                        binding.btnSave.isEnabled = true
                        Snackbar.make(
                            binding.root,
                            getString(R.string.text_add_pickup_address_error),
                            Snackbar.LENGTH_SHORT
                        ).show()
                        Timber.e(it.exception)
                        loadingDialog.hideDialog()
                    }
                }
            }
        })

    }

    private fun setupSubdistrictOrTownSpinner() {
        binding.sSubdistrictOrVillage.adapter = subdistrictOrVillageAdapter
        binding.sSubdistrictOrVillage.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (id.toInt() == -1) return
                viewModel.updateSubdistrictOrVillageFormField((view as TextView).text.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        viewModel.subdistrictOrVillageRows.observe(viewLifecycleOwner, {
            it?.let {
                subdistrictOrVillageAdapter.refreshList(it.toTypedArray())
            }
        })
    }

    private fun setupDistrictOrTownSpinner() {
        binding.sDistrictOrTown.adapter = districtOrTownAdapter
        binding.sDistrictOrTown.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (id.toInt() == -1) return
                viewModel.updateDistrictOrTownFormField((view as TextView).text.toString())
                val parentCode = String.format("%03d", id.toInt())
                viewModel.updateSubdistrictOrVillage(parentCode)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        viewModel.districtOrTownRows.observe(viewLifecycleOwner, {
            it?.let {
                districtOrTownAdapter.refreshList(it.toTypedArray())
            }
        })
    }

    private fun setupProvinceOrCitySpinner() {
        binding.sProvinceOrCity.adapter = provinceOrCityAdapter
        binding.sProvinceOrCity.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (id.toInt() == -1) return
                viewModel.updateProvinceOrCityFormField((view as TextView).text.toString())
                val parentCode = String.format("%02d", id.toInt())
                viewModel.updateDistrictOrTown(parentCode)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        viewModel.provinceOrCityRows.observe(viewLifecycleOwner, {
            it?.let {
                provinceOrCityAdapter.refreshList(it.toTypedArray())
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

