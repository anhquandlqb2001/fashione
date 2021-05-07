/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import vn.quanprolazer.fashione.adapters.CheckoutItemAdapter
import vn.quanprolazer.fashione.data.domain.model.Resource
import vn.quanprolazer.fashione.databinding.FragmentCheckoutBinding
import vn.quanprolazer.fashione.viewmodels.CheckoutSharedViewModel
import vn.quanprolazer.fashione.viewmodels.CheckoutViewModel
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

    private val checkoutSharedViewModel: CheckoutSharedViewModel by activityViewModels()

    private val adapter: CheckoutItemAdapter by lazy {
        CheckoutItemAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater,
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

        binding.rvCheckout.adapter = adapter

        checkoutViewModel.checkoutItems.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })

        checkoutViewModel.navigateToPickupAddress.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController()
                    .navigate(CheckoutFragmentDirections.actionCheckoutFragmentToPickupAddressFragment())
                checkoutViewModel.doneNavigate()
            }
        })

        checkoutViewModel.defaultCheckoutAddress.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        binding.address = it.data
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

        checkoutSharedViewModel.addressPickup.observe(viewLifecycleOwner, {
            it?.let {
                binding.llAddressData.visibility = View.VISIBLE
                binding.tvNoDefault.visibility = View.GONE
                binding.address = it
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}