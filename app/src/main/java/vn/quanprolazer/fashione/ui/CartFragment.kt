/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import vn.quanprolazer.fashione.adapters.CartItemAdapter
import vn.quanprolazer.fashione.data.domain.model.CartItem
import vn.quanprolazer.fashione.data.domain.model.Result
import vn.quanprolazer.fashione.databinding.FragmentCartBinding
import vn.quanprolazer.fashione.viewmodels.CartViewModel

@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null

    private val binding: FragmentCartBinding get() = _binding!!

    private val viewModel: CartViewModel by viewModels()

    private val adapter: CartItemAdapter by lazy { CartItemAdapter() }

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
        binding.rvCart.adapter = adapter
        binding.rvCart.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        observeCartItems()
    }

    private fun observeCartItems() {
        viewModel.cartItems.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Result.Success -> {
                        observeCartItemsSuccess(it)
                    }
                    is Result.Loading -> {
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

    private fun observeCartItemsSuccess(it: Result.Success<MutableList<CartItem>>) {
        viewModel.updateCartItemsImage()
        Handler(Looper.getMainLooper()).postDelayed({
            if (it.data.size == 0) {
                shouldDisplayBlankCart(View.VISIBLE)
                shouldDisplayLoadingProgress(View.GONE)
                return@postDelayed
            }
            shouldDisplayBlankCart(View.GONE)
            adapter.submitList(it.data)
            shouldDisplayLoadingProgress(View.GONE)
            binding.rvCart.visibility = View.VISIBLE
        }, 1000)
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