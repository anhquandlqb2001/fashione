/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import vn.quanprolazer.fashione.adapters.PurchaseItemAdapter
import vn.quanprolazer.fashione.data.domain.model.Resource
import vn.quanprolazer.fashione.databinding.FragmentPurchaseBinding
import vn.quanprolazer.fashione.viewmodels.PurchaseViewModel

class PurchaseFragment : Fragment() {
    private var _binding: FragmentPurchaseBinding? = null

    private val binding: FragmentPurchaseBinding get() = _binding!!

    private val purchaseViewModel: PurchaseViewModel by activityViewModels()

    private val purchaseItemAdapter: PurchaseItemAdapter by lazy { PurchaseItemAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View { // Inflate the layout for this fragment
        _binding = FragmentPurchaseBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvPurchase.adapter = purchaseItemAdapter

        purchaseViewModel.purchaseItems.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        Handler(Looper.getMainLooper()).postDelayed({
                            purchaseItemAdapter.submitList(it.data)
                        }, 1000)
                        purchaseViewModel.getPurchaseItemsImage()
                        purchaseItemAdapter.submitList(it.data)
                        binding.rvPurchase.visibility = View.VISIBLE
                        binding.fLoading.cpLoading.visibility = View.GONE
                    }
                    is Resource.Loading -> {
                        binding.fLoading.cpLoading.visibility = View.VISIBLE
                        binding.rvPurchase.visibility = View.GONE
                    }
                    is Resource.Error -> {
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