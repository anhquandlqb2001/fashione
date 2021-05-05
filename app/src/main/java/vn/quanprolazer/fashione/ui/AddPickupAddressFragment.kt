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
import dagger.hilt.android.AndroidEntryPoint
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.databinding.FragmentAddPickupAddressBinding
import vn.quanprolazer.fashione.viewmodels.AddPickupAddressViewModel

@AndroidEntryPoint
class AddPickupAddressFragment : Fragment() {

    private var _binding: FragmentAddPickupAddressBinding? = null

    private val binding: FragmentAddPickupAddressBinding get() = _binding!!

    private val viewModel: AddPickupAddressViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddPickupAddressBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

