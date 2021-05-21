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
import vn.quanprolazer.fashione.databinding.FragmentPersonalBinding
import vn.quanprolazer.fashione.domain.models.OrderStatus
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


        viewModel.navigateToPurchaseMenu.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController()
                    .navigate(PersonalFragmentDirections.actionPersonalFragmentToPurchaseMenuFragment())
                viewModel.doneNavigateToPurchaseMenu()
            }
        })

        viewModel.navigateToPurchaseMenuByDeliveryUI.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController()
                    .navigate(PersonalFragmentDirections.actionPersonalFragmentToPurchaseMenuFragment(it))
                viewModel.doneNavigateToPurchaseMenuByDeliveryUI()
            }
        })

        viewModel.deliveryStatus.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        enumValues<OrderStatus>().forEach { status ->
                            when (status) {
                                OrderStatus.CONFIRMING -> {
                                    val expect =
                                        it.data.find { deliveryStatus -> deliveryStatus.status == OrderStatus.CONFIRMING }
                                    binding.ibvConfirming.badgeValue = expect?.quantity ?: 0
                                }
                                OrderStatus.COLLECTING -> {
                                    val expect =
                                        it.data.find { deliveryStatus -> deliveryStatus.status == OrderStatus.COLLECTING }
                                    binding.ibvCollecting.badgeValue = expect?.quantity ?: 0
                                }
                                OrderStatus.DELIVERING -> {
                                    val expect =
                                        it.data.find { deliveryStatus -> deliveryStatus.status == OrderStatus.DELIVERING }
                                    binding.ibvDelivering.badgeValue = expect?.quantity ?: 0
                                }
                                OrderStatus.DELIVERED -> {
                                    val expect =
                                        it.data.find { deliveryStatus -> deliveryStatus.status == OrderStatus.DELIVERED }
                                    binding.ibvDelivered.badgeValue = expect?.quantity ?: 0
                                }
                            }
                        }
                    }
                }
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}