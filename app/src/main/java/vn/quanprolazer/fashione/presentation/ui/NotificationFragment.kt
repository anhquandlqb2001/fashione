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
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import vn.quanprolazer.fashione.databinding.FragmentNotificationBinding
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.presentation.adapters.NotificationGroupAdapter
import vn.quanprolazer.fashione.presentation.adapters.NotificationItemAdapter
import vn.quanprolazer.fashione.presentation.utilities.MarginItemDecoration
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

    private val notificationItemAdapter: NotificationItemAdapter by lazy { NotificationItemAdapter() }
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

        binding.rvNotification.apply {
            adapter = notificationItemAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context, DividerItemDecoration.VERTICAL
                )
            )
            addItemDecoration(MarginItemDecoration(20))
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.notificationOrderStatusType.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Resource.Success -> notificationItemAdapter.submitList(it.data)
                    is Resource.Error -> Timber.e(it.exception)

                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}