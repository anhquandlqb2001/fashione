/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.adapters.CartItemAdapter
import vn.quanprolazer.fashione.adapters.CartItemListener
import vn.quanprolazer.fashione.data.domain.model.CartItem
import vn.quanprolazer.fashione.data.domain.model.Resource
import vn.quanprolazer.fashione.databinding.FragmentCartBinding
import vn.quanprolazer.fashione.utilities.ItemSwipeHandler
import vn.quanprolazer.fashione.viewmodels.BottomCheckoutViewModel
import vn.quanprolazer.fashione.viewmodels.CartViewModel
import vn.quanprolazer.fashione.viewmodels.CheckoutSharedViewModel


@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding: FragmentCartBinding get() = _binding!!

    private val viewModel: CartViewModel by viewModels()

    private val checkoutSharedViewModel: CheckoutSharedViewModel by viewModels()

    private val bottomCheckoutViewModel: BottomCheckoutViewModel by viewModels()

    private lateinit var adapter: CartItemAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        setupViewModel()

        return binding.root
    }

    private fun setupViewModel() = binding.apply {
        viewModel = viewModel
        fBottomCheckout.sharedViewModel = checkoutSharedViewModel
        fBottomCheckout.viewModel = bottomCheckoutViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViewAdapter()

        viewModel.cartItems.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        if (viewModel.flagFirstTimeLoad) {
                            viewModel.updateCartItemsImage()
                            viewModel.updateCartItemsProductName()
                            viewModel.doneFirstTimeLoad()
                        }

                        if (it.data.size == 0) {
                            updateBlankCartFragmentVisibility(View.VISIBLE)
                            updateLoadingProgressVisibility(View.GONE)
                            updateBottomCheckoutVisibility(false)
                            return@let
                        }

                        checkoutSharedViewModel.updateOrderData(it.data)

                        updateBlankCartFragmentVisibility(View.GONE)

                        Handler(Looper.getMainLooper()).postDelayed({
                            updateBottomCheckoutVisibility(true)
                            adapter.submitList(it.data)
                            updateLoadingProgressVisibility(View.INVISIBLE)
                            updateBottomCheckoutVisibility(true)
                            binding.rvCart.visibility = View.VISIBLE
                        }, 800)
                    }
                    is Resource.Loading -> {
                        updateCartUILoading()
                    }
                    else -> {
                    }
                }
            }
        })

        observeNavigateToCheckout()
    }

    private fun setupRecyclerViewAdapter() {
        adapter = CartItemAdapter(object : CartItemListener() {
            override fun quantityControlClick(cartItem: CartItem, value: Int) {
                viewModel.onQuantityControlClick(cartItem, value)
            }

            override fun checkBoxClick(cartItem: CartItem) {
                viewModel.refreshList()
            }
        })
        binding.rvCart.adapter = adapter
        binding.rvCart.layoutManager =
            WrapContentLinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        setupItemTouchHelper()
    }

    private fun setupItemTouchHelper() {
        ItemTouchHelper(ItemSwipeHandler(
            adapter,
            ContextCompat.getDrawable(requireContext(), R.drawable.baseline_delete_black_36dp)
        ) { _, item ->
            viewModel.removeCartItem(item.id)
            showUndoSnackbar(item)
        }).attachToRecyclerView(binding.rvCart)
    }

    private fun observeNavigateToCheckout() {
        bottomCheckoutViewModel.navigateToCheckoutScreen.observe(viewLifecycleOwner, {
            it?.let {
                val options = NavOptions.Builder().setLaunchSingleTop(true).build()
                this.findNavController().navigate(
                    CartFragmentDirections.actionCartFragmentToCheckoutFragment(it.toTypedArray()),
                    options
                )
                bottomCheckoutViewModel.onNavigateSuccess()
            }
        })
    }

    private fun showUndoSnackbar(cartItem: CartItem) {
        val snackbar: Snackbar = Snackbar.make(
            binding.root, R.string.delete_success_text, Snackbar.LENGTH_LONG
        )
        snackbar.setAction(R.string.snack_bar_undo_text) { _ ->
            viewModel.undoDeleteCartItem(
                cartItem
            )
        }
        snackbar.show()
    }

    private fun updateCartUILoading() {
        updateBlankCartFragmentVisibility(View.GONE)
        updateLoadingProgressVisibility()
        updateBottomCheckoutVisibility(false)
    }

    private fun updateBottomCheckoutVisibility(value: Boolean) =
        bottomCheckoutViewModel.updateVisibleBottomCheckout(value)


    private fun updateLoadingProgressVisibility(visibility: Int = View.VISIBLE) {
        binding.cpLoading.visibility = visibility
    }

    private fun updateBlankCartFragmentVisibility(visibility: Int) {
        binding.fBlankCart.visibility = visibility
    }

    class WrapContentLinearLayoutManager(context: Context, orient: Int, attachToRoot: Boolean) :
        LinearLayoutManager(context, orient, attachToRoot) {
        override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
            try {
                super.onLayoutChildren(recycler, state)
            } catch (e: IndexOutOfBoundsException) {
                Timber.e(e)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}