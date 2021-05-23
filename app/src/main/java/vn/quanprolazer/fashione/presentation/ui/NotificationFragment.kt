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
import dagger.hilt.android.AndroidEntryPoint
import vn.quanprolazer.fashione.databinding.FragmentNotificationBinding
import vn.quanprolazer.fashione.presentation.adapters.NotificationGroupAdapter
import vn.quanprolazer.fashione.presentation.viewmodels.NotificationViewModel
import javax.inject.Inject

@AndroidEntryPoint
class NotificationFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null

    /** This property is only valid between onCreateView and onDestroyView. */
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: NotificationViewModel.AssistedFactory
    private val viewModel: NotificationViewModel by viewModels {
        NotificationViewModel.provideFactory(
            viewModelFactory,
            NotificationFragmentArgs.fromBundle(requireArguments()).notificationOverviews.toList()
        )
    }

    private val notificationTypeAdapter: NotificationGroupAdapter by lazy { NotificationGroupAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.rvNotificationGroup.apply {
            adapter = notificationTypeAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel.notificationType.observe(viewLifecycleOwner, {
//            it?.let {
//                Timber.d(it.toString())
//            }
//        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}