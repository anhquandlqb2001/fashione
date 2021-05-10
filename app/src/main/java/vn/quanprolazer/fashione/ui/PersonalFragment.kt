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
import vn.quanprolazer.fashione.databinding.FragmentPersonalBinding
import vn.quanprolazer.fashione.viewmodels.PersonalViewModel

@AndroidEntryPoint
class PersonalFragment : Fragment() {

    private var _binding: FragmentPersonalBinding? = null

    private val binding: FragmentPersonalBinding get() = _binding!!

    private val viewModel: PersonalViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater,
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

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}