/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import vn.quanprolazer.fashione.databinding.FragmentPickupAddressBinding
import vn.quanprolazer.fashione.domain.models.PickupAddress
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.presentation.adapters.OnPickupAddressListener
import vn.quanprolazer.fashione.presentation.adapters.PickupAddressAdapter
import vn.quanprolazer.fashione.presentation.viewmodels.CheckoutSharedViewModel
import vn.quanprolazer.fashione.presentation.viewmodels.PickupAddressViewModel

@AndroidEntryPoint
class PickupAddressFragment : Fragment() {

    private var _binding: FragmentPickupAddressBinding? = null

    private val binding: FragmentPickupAddressBinding get() = _binding!!

    private val viewModel: PickupAddressViewModel by viewModels()

    private val checkoutSharedViewModel: CheckoutSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View { // Inflate the layout for this fragment
        _binding = FragmentPickupAddressBinding.inflate(inflater, container, false)


        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pickupAddressAdapter = PickupAddressAdapter(object : OnPickupAddressListener() {
            override fun onClickUpdateAddress(pickupAddress: PickupAddress) {
                TODO("Not yet implemented")
            }

            override fun onClickChoosePickupAddress(pickupAddress: PickupAddress) {
                viewModel.onNavigateBackToCheckout(pickupAddress)
            }
        })

        binding.rvPickupAddress.adapter = pickupAddressAdapter

        viewModel.pickupAddresses.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Resource.Success -> pickupAddressAdapter.submitList(it.data)
                    is Resource.Loading -> Timber.i("Loading")
                    else -> {
                    }
                }
            }
        })

        viewModel.navigateToAddPickupAddress.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController()
                    .navigate(PickupAddressFragmentDirections.actionPickupAddressFragmentToAddPickupAddressFragment())
                viewModel.doneNavigateToAddPickupAddress()
            }
        })

        viewModel.navigateBackToCheckout.observe(viewLifecycleOwner, {
            it?.let {
                checkoutSharedViewModel.updateAddressPickup(it)
                this.findNavController().popBackStack()
                viewModel.doneNavigateBackToCheckout()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}