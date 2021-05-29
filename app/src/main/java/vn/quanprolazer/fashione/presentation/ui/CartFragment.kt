/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.databinding.FragmentCartBinding
import vn.quanprolazer.fashione.domain.models.CartItem
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.presentation.adapters.CartItemAdapter
import vn.quanprolazer.fashione.presentation.adapters.CartItemListener
import vn.quanprolazer.fashione.presentation.utilities.ItemSwipeHandler
import vn.quanprolazer.fashione.presentation.utilities.SpacesItemDecoration
import vn.quanprolazer.fashione.presentation.viewmodels.BottomCheckoutViewModel
import vn.quanprolazer.fashione.presentation.viewmodels.CartViewModel
import vn.quanprolazer.fashione.presentation.viewmodels.CheckoutSharedViewModel


@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding: FragmentCartBinding get() = _binding!!

    private val viewModel: CartViewModel by viewModels()

    private val checkoutSharedViewModel: CheckoutSharedViewModel by viewModels()

    private val bottomCheckoutViewModel: BottomCheckoutViewModel by viewModels()

    private lateinit var adapter: CartItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        setupViewModel()
        setupRecyclerViewAdapter()

        return binding.root
    }

    private fun setupViewModel() = binding.apply {
        viewModel = viewModel
        fBottomCheckout.sharedViewModel = checkoutSharedViewModel
        fBottomCheckout.viewModel = bottomCheckoutViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.cartItems.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        if (it.data.size == 0) {
                            updateBlankCartFragmentVisibility(View.VISIBLE)
                            updateLoadingProgressVisibility(View.GONE)
                            return@let
                        }

                        adapter.submitList(it.data)

                        checkoutSharedViewModel.updateOrderData(it.data)

                        updateBlankCartFragmentVisibility(View.GONE)
                        updateLoadingProgressVisibility(View.INVISIBLE)

                        binding.rvCart.visibility = View.VISIBLE
                    }
                    is Resource.Loading -> {
                        updateCartUILoading()
                    }
                    is Resource.Error -> {
                        binding.llNotLogin.visibility = View.VISIBLE
                        updateBlankCartFragmentVisibility(View.GONE)
                        updateLoadingProgressVisibility(View.INVISIBLE)
                        when (it.exception.message) {
                            "NOT_LOGIN" -> {
                                Snackbar.make(
                                    binding.root,
                                    "Bạn phải đăng nhập trước",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        })

        checkoutSharedViewModel.orderData.observe(viewLifecycleOwner, {
            it?.let {
                Timber.i(it.items.isEmpty().toString())
                if (it.items.isEmpty()) {
                    updateBottomCheckoutVisibility(false)
                } else {
                    updateBottomCheckoutVisibility(true)
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
        setupItemTouchHelper()

        binding.rvCart.adapter = adapter
        binding.rvCart.layoutManager =
            WrapContentLinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvCart.addItemDecoration(SpacesItemDecoration(20))
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
                this.findNavController().navigate(
                    CartFragmentDirections.actionCartFragmentToCheckoutFragment(it.toTypedArray())
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