/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.databinding.FragmentCheckoutBinding
import vn.quanprolazer.fashione.databinding.FragmentExtendNotificationBinding
import vn.quanprolazer.fashione.presentation.viewmodels.ExtendNotificationViewModel
import javax.inject.Inject

class ExtendNotificationFragment : Fragment() {

    private var _binding: FragmentExtendNotificationBinding? = null

    private val binding: FragmentExtendNotificationBinding get() = _binding!!

    @Inject
    lateinit var factory: ExtendNotificationViewModel.AssistedFactory
    private val viewModel: ExtendNotificationViewModel by viewModels {
        ExtendNotificationViewModel.provideFactory(
            factory,
            ExtendNotificationFragmentArgs.fromBundle(requireArguments()).typeId
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExtendNotificationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}