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
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import vn.quanprolazer.fashione.databinding.FragmentNotificationBinding
import vn.quanprolazer.fashione.domain.models.NotificationTypeEnum
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.presentation.adapters.NotificationGroupAdapter
import vn.quanprolazer.fashione.presentation.adapters.NotificationGroupItemListener
import vn.quanprolazer.fashione.presentation.adapters.NotificationItemAdapter
import vn.quanprolazer.fashione.presentation.utilities.LoadingDialog
import vn.quanprolazer.fashione.presentation.utilities.MarginItemDecoration
import vn.quanprolazer.fashione.presentation.utilities.bindings.notificationOverviewResponse
import vn.quanprolazer.fashione.presentation.viewmodels.NotificationViewModel

@AndroidEntryPoint
class NotificationFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null

    /** This property is only valid between onCreateView and onDestroyView. */
    private val binding get() = _binding!!

    private val viewModel: NotificationViewModel by viewModels()

    private val notificationTypeAdapter: NotificationGroupAdapter by lazy {
        NotificationGroupAdapter(object : NotificationGroupItemListener() {
            override fun onClick(typeId: String) {
                viewModel.onNavigateToExtendNotification(typeId)
            }
        })
    }

    private val notificationItemAdapter: NotificationItemAdapter by lazy { NotificationItemAdapter() }

    private val loadingDialog: LoadingDialog by lazy { LoadingDialog(requireActivity()) }

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

        viewModel.notificationOverview.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        binding.rvNotificationGroup.notificationOverviewResponse(it.data)
                        viewModel.fetchNotificationsOrderStatus(it.data.notifications.filter { notificationOverview -> notificationOverview.type.name == NotificationTypeEnum.ORDER_STATUS }[0].type.id)
                    }
                    is Resource.Loading -> {
                        loadingDialog.showDialog()
                    }
                    is Resource.Error -> {
                        Timber.e(it.exception)
                        binding.llNotLogin.visibility = View.VISIBLE
                        loadingDialog.hideDialog()
                        when (it.exception.message) {
                            "NOT_LOGIN" -> {
                                Snackbar.make(
                                    binding.root,
                                    "Bạn phải đăng nhập trước",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
//                        this.findNavController().popBackStack()
                    }
                }
            }
        })

        viewModel.notificationOrderStatus.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        binding.llMain.visibility = View.VISIBLE
                        notificationItemAdapter.submitList(it.data)
                        loadingDialog.hideDialog()
                    }
                    is Resource.Loading -> {
                    }
                    is Resource.Error -> {
                        loadingDialog.hideDialog()
                        when (it.exception.message) {
                            "NOT_LOGIN" -> {
                                Snackbar.make(
                                    binding.root,
                                    "Bạn phải đăng nhập trước",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
                        this.findNavController().popBackStack()
                    }
                }
            }
        })

        viewModel.navigateToExtendNotification.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController().navigate(
                    NotificationFragmentDirections.actionNotificationFragmentToExtendNotificationFragment(
                        it
                    )
                )
                viewModel.doneNavigateToExtendNotification()
            }
        })


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}