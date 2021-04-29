/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.databinding.FragmentPickupAddressBinding
import vn.quanprolazer.fashione.viewmodels.PickupAddressViewModel

@AndroidEntryPoint
class PickupAddressFragment : Fragment() {

    private var _binding: FragmentPickupAddressBinding? = null

    private val binding: FragmentPickupAddressBinding get() = _binding!!

    private val viewModel: PickupAddressViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPickupAddressBinding.inflate(inflater, container, false)


        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigateToAddPickupAddress.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController().navigate(PickupAddressFragmentDirections.actionPickupAddressFragmentToAddPickupAddressFragment())
                viewModel.doneNavigateToAddPickupAddress()
            }
        })

    }



}