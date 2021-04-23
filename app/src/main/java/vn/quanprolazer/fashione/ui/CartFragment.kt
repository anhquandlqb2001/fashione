/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.ui

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.adapters.CartItemAdapter
import vn.quanprolazer.fashione.adapters.CartItemQuantityControlClick
import vn.quanprolazer.fashione.adapters.ItemSwipeHandler
import vn.quanprolazer.fashione.data.domain.model.CartItem
import vn.quanprolazer.fashione.data.domain.model.Resource
import vn.quanprolazer.fashione.databinding.FragmentCartBinding
import vn.quanprolazer.fashione.viewmodels.CartViewModel


@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null

    private val binding: FragmentCartBinding get() = _binding!!

    private val viewModel: CartViewModel by viewModels()

    private lateinit var adapter: CartItemAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CartItemAdapter(CartItemQuantityControlClick { cartItem, value ->
            viewModel.onQuantityControlClick(cartItem, value)
        })

        ItemTouchHelper(ItemSwipeHandler(adapter, ContextCompat.getDrawable(requireContext(), R.drawable.baseline_delete_black_36dp)) {
            viewModel.removeCartItem(it.id)
        }).attachToRecyclerView(binding.rvCart)

        binding.rvCart.adapter = adapter
        binding.rvCart.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        observeCartItems()
    }


    private fun observeCartItems() {
        viewModel.cartItems.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        observeCartItemsSuccess(it)
                    }
                    is Resource.Loading -> {
                        observeCartItemLoading()
                    }
                    else -> {
                        // Error handler
                    }
                }
            }
        })
    }

    private fun observeCartItemLoading() {
        shouldDisplayBlankCart(View.GONE)
        shouldDisplayLoadingProgress()
    }

    private fun observeCartItemsSuccess(it: Resource.Success<MutableList<CartItem>>) {
        if (viewModel.flagFirstTime.value == null) return
        viewModel.updateCartItemsImage()
        viewModel.updateCartItemsProductName()
        Handler(Looper.getMainLooper()).postDelayed({
            if (it.data.size == 0) {
                shouldDisplayBlankCart(View.VISIBLE)
                shouldDisplayLoadingProgress(View.GONE)
                return@postDelayed
            }
            shouldDisplayBlankCart(View.GONE)
            shouldDisplayLoadingProgress(View.GONE)
            adapter.submitList(it.data)
            binding.rvCart.visibility = View.VISIBLE
        }, 1000)
        viewModel.offFlagFirstTime()
    }

    private fun shouldDisplayLoadingProgress(visibility: Int = View.VISIBLE) {
        binding.cpLoading.visibility = visibility
    }

    private fun shouldDisplayBlankCart(visibility: Int) {
        binding.fBlankCart.visibility = visibility
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}