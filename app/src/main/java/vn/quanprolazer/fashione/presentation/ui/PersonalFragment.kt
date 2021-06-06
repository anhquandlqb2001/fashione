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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import vn.quanprolazer.fashione.databinding.FragmentPersonalBinding
import vn.quanprolazer.fashione.domain.models.OrderItemStatusType
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.presentation.viewmodels.PersonalViewModel

@AndroidEntryPoint
class PersonalFragment : Fragment() {

    private var _binding: FragmentPersonalBinding? = null

    private val binding: FragmentPersonalBinding get() = _binding!!

    private val viewModel: PersonalViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View { // Inflate the layout for this fragment
        _binding = FragmentPersonalBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        observeNavigateToPurchaseDefault()

        observeNavigateToPurchaseSpecifyTab()

        observeDeliveryStatus()

        viewModel.navigateToPickupAddress.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController()
                    .navigate(PersonalFragmentDirections.actionPersonalFragmentToPickupAddressFragment())
                viewModel.doneNavigateToPickupAddress()
            }
        })
    }

    private fun observeDeliveryStatus() {
        viewModel.deliveryStatus.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        enumValues<OrderItemStatusType>().forEach { status ->
                            when (status) {
                                OrderItemStatusType.CONFIRMING -> {
                                    val expect =
                                        it.data.find { deliveryStatus -> deliveryStatus.status == OrderItemStatusType.CONFIRMING }
                                    binding.ibvConfirming.badgeValue = expect?.quantity ?: 0
                                }
                                OrderItemStatusType.COLLECTING -> {
                                    val expect =
                                        it.data.find { deliveryStatus -> deliveryStatus.status == OrderItemStatusType.COLLECTING }
                                    binding.ibvCollecting.badgeValue = expect?.quantity ?: 0
                                }
                                OrderItemStatusType.DELIVERING -> {
                                    val expect =
                                        it.data.find { deliveryStatus -> deliveryStatus.status == OrderItemStatusType.DELIVERING }
                                    binding.ibvDelivering.badgeValue = expect?.quantity ?: 0
                                }
                                OrderItemStatusType.DELIVERED -> {
                                    val expect =
                                        it.data.find { deliveryStatus -> deliveryStatus.status == OrderItemStatusType.DELIVERED }
                                    binding.ibvDelivered.badgeValue = expect?.quantity ?: 0
                                }
                                else -> {
                                }
                            }
                        }
                    }
                    is Resource.Error -> {
                        Timber.e(it.exception)
                    }
                    Resource.Loading -> {
                    }
                }
            }
        })
    }

    private fun observeNavigateToPurchaseSpecifyTab() {
        viewModel.navigateToPurchaseMenuByDeliveryUI.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController()
                    .navigate(
                        PersonalFragmentDirections.actionPersonalFragmentToPurchaseMenuFragment(
                            it
                        )
                    )
                viewModel.doneNavigateToPurchaseMenuByDeliveryUI()
            }
        })
    }

    private fun observeNavigateToPurchaseDefault() {
        viewModel.navigateToPurchaseMenu.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController()
                    .navigate(PersonalFragmentDirections.actionPersonalFragmentToPurchaseMenuFragment())
                viewModel.doneNavigateToPurchaseMenu()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}