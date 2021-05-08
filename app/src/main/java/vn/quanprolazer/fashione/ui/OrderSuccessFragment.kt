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
import vn.quanprolazer.fashione.databinding.FragmentOrderSuccessBinding
import vn.quanprolazer.fashione.viewmodels.OrderSuccessViewModel

class OrderSuccessFragment : Fragment() {

    private var _binding: FragmentOrderSuccessBinding? = null
    private val binding: FragmentOrderSuccessBinding get() = _binding!!

    private val viewModel: OrderSuccessViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View { // Inflate the layout for this fragment
        _binding = FragmentOrderSuccessBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigateToHome.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController()
                    .navigate(OrderSuccessFragmentDirections.actionOrderSuccessFragmentToHomeFragment())
                viewModel.doneNavigateToHome()
            }
        })
    }
}