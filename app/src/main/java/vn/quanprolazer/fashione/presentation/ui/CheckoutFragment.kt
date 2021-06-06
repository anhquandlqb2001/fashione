/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.get
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.databinding.FragmentCheckoutBinding
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.presentation.adapters.CheckoutItemAdapter
import vn.quanprolazer.fashione.presentation.utilities.LoadingDialog
import vn.quanprolazer.fashione.presentation.utilities.SpacesItemDecoration
import vn.quanprolazer.fashione.presentation.viewmodels.AddPickupAddressSharedViewModel
import vn.quanprolazer.fashione.presentation.viewmodels.CheckoutSharedViewModel
import vn.quanprolazer.fashione.presentation.viewmodels.CheckoutViewModel
import javax.inject.Inject


@AndroidEntryPoint
class CheckoutFragment : Fragment() {

    private var _binding: FragmentCheckoutBinding? = null

    private val binding: FragmentCheckoutBinding get() = _binding!!

    @Inject
    lateinit var checkoutViewModelFactory: CheckoutViewModel.AssistedFactory
    private val checkoutViewModel: CheckoutViewModel by viewModels {
        CheckoutViewModel.provideFactory(
            checkoutViewModelFactory,
            CheckoutFragmentArgs.fromBundle(requireArguments()).checkoutItems.toList()
        )
    }

    private val addPickupAddressSharedViewModel: AddPickupAddressSharedViewModel by activityViewModels()

    private val checkoutSharedViewModel: CheckoutSharedViewModel by activityViewModels()

    private val adapter: CheckoutItemAdapter by lazy {
        CheckoutItemAdapter()
    }

    private val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.checkoutViewModel = checkoutViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCheckoutRecycleView()

        observeCheckoutItems()

        observeToChoosePickupAddress()

        observeGetDefaultPickupAddressId()

        observePickupAddress()

        observePickupAddressId()

        observeNavigateToOrderSuccess()

        checkoutViewModel.totalProductPrice.observe(viewLifecycleOwner, {
            it?.let {
                checkoutViewModel.updateProductPrice(it)
            }
        })

        checkoutViewModel.totalShipPrice.observe(viewLifecycleOwner, {
            it?.let {
                checkoutViewModel.updateShipPrice(it)
            }
        })

    }

    private fun observeNavigateToOrderSuccess() {
        checkoutViewModel.navigateToOrderSuccess.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        Handler(Looper.getMainLooper()).postDelayed({
                            loadingDialog.hideDialog()
                            this.findNavController()
                                .navigate(CheckoutFragmentDirections.actionCheckoutFragmentToOrderSuccessFragment())
                            checkoutViewModel.doneNavigateToOrderSuccess()
                        }, 800)
                    }
                    is Resource.Loading -> {
                        loadingDialog.showDialog()
                    }
                    is Resource.Error -> {
                        loadingDialog.hideDialog()
                    }
                }
            }
        })
    }

    private fun observePickupAddressId() {
        checkoutViewModel.pickupAddressId.observe(viewLifecycleOwner, {
            if (it.isNullOrEmpty()) {
                binding.apply {
                    llConfirm.visibility = View.GONE
                    llPickupAddress.visibility = View.GONE
                    tvPickupAddressRequire.visibility = View.VISIBLE

                }
            } else {
                binding.apply {
                    llConfirm.visibility = View.VISIBLE
                    llPickupAddress.visibility = View.VISIBLE
                    tvPickupAddressRequire.visibility = View.GONE
                }
            }
        })
    }

    private fun observePickupAddress() {
        checkoutSharedViewModel.addressPickup.observe(viewLifecycleOwner, {
            it?.let {
                binding.llAddressData.visibility = View.VISIBLE
                binding.tvNoDefault.visibility = View.GONE
                binding.address = it
                checkoutViewModel.updateAddressId(it.id)
            }
        })
    }

    private fun observeGetDefaultPickupAddressId() {
        checkoutViewModel.defaultCheckoutAddress.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        binding.address = it.data
                        checkoutViewModel.updateAddressId(it.data.id)
                    }
                    is Resource.Error -> {
                        if (it.exception.message == "NOT_FOUND") {
                            binding.llAddressData.visibility = View.GONE
                            binding.tvNoDefault.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })
    }

    private fun observeToChoosePickupAddress() {
        checkoutViewModel.navigateToPickupAddress.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController()
                    .navigate(CheckoutFragmentDirections.actionCheckoutFragmentToPickupAddressFragment())
                addPickupAddressSharedViewModel.setDestinationToNavigate(this.findNavController().graph[R.id.checkoutFragment].id)
                checkoutViewModel.doneNavigate()
            }
        })
    }

    private fun observeCheckoutItems() {
        checkoutViewModel.checkoutItems.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

    private fun setupCheckoutRecycleView() {
        binding.rvCheckout.addItemDecoration(
            DividerItemDecoration(
                context, DividerItemDecoration.VERTICAL
            )
        )
        binding.rvCheckout.addItemDecoration(SpacesItemDecoration(20))
        binding.rvCheckout.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}