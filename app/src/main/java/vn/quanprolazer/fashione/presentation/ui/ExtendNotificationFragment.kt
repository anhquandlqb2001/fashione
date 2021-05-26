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
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import vn.quanprolazer.fashione.databinding.FragmentExtendNotificationBinding
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.presentation.adapters.NotificationExtendItemAdapter
import vn.quanprolazer.fashione.presentation.utilities.MarginItemDecoration
import vn.quanprolazer.fashione.presentation.viewmodels.ExtendNotificationViewModel
import javax.inject.Inject

@AndroidEntryPoint
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

    private val adapter: NotificationExtendItemAdapter by lazy { NotificationExtendItemAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExtendNotificationBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.rvNotificationExtend.adapter = adapter
        binding.rvNotificationExtend.addItemDecoration(MarginItemDecoration(20))
        binding.rvNotificationExtend.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                RecyclerView.VERTICAL
            )
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.notificationsOfPromotion.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        if (it.data.isEmpty()) {
                            binding.tvNoItem.visibility = View.VISIBLE
                            binding.rvNotificationExtend.visibility = View.GONE
                        } else {
                            binding.tvNoItem.visibility = View.GONE
                            binding.rvNotificationExtend.visibility = View.VISIBLE
                            adapter.submitList(it.data)
                        }
                    }
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