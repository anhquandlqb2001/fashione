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
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import vn.quanprolazer.fashione.adapters.CheckoutItemAdapter
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
        CheckoutViewModel.provideFactory(checkoutViewModelFactory, CheckoutFragmentArgs.fromBundle(requireArguments()).checkoutItems.toList())
    }

    private val checkoutSharedViewModel: CheckoutSharedViewModel by viewModels()

    private val adapter: CheckoutItemAdapter by lazy {
        CheckoutItemAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            checkoutViewModel = checkoutViewModel
        }

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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}