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
import timber.log.Timber
import vn.quanprolazer.fashione.adapters.CartItemAdapter
import vn.quanprolazer.fashione.data.domain.model.Result
import vn.quanprolazer.fashione.databinding.FragmentCartBinding
import vn.quanprolazer.fashione.viewmodels.CartViewModel
import java.util.*

@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null

    private val binding: FragmentCartBinding get() = _binding!!

    private val viewModel: CartViewModel by viewModels()

    private val adapter = CartItemAdapter()

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

        viewModel.cartItem.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Result.Success -> {
                        Handler(Looper.getMainLooper()).postDelayed({
                            adapter.submitList(it.data)
                            binding.rvCart.visibility = View.VISIBLE
                            binding.cpLoading.visibility = View.INVISIBLE
                        }, 1000)
                    }
                    is Result.Loading -> {
                        binding.cpLoading.visibility = View.VISIBLE
                    }
                    else -> {
                        // Error handler
                    }
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}