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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import timber.log.Timber
import vn.quanprolazer.fashione.adapters.PurchaseItemAdapter
import vn.quanprolazer.fashione.adapters.PurchaseItemListener
import vn.quanprolazer.fashione.data.domain.model.Purchase
import vn.quanprolazer.fashione.data.domain.model.Resource
import vn.quanprolazer.fashione.databinding.FragmentPurchaseBinding
import vn.quanprolazer.fashione.utilities.ViewDialog
import vn.quanprolazer.fashione.viewmodels.PurchaseViewModel

class PurchaseFragment : Fragment() {
    private var _binding: FragmentPurchaseBinding? = null

    private val binding: FragmentPurchaseBinding get() = _binding!!

    private val purchaseViewModel: PurchaseViewModel by activityViewModels()

    private val purchaseItemAdapter: PurchaseItemAdapter by lazy {
        PurchaseItemAdapter(object : PurchaseItemListener() {
            override fun onClick(purchase: Purchase) {
                purchaseViewModel.onClickReOrder(purchase)
            }
        })
    }

    private val loadingDialog: ViewDialog by lazy {
        ViewDialog(requireActivity())
    }

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
        binding.rvPurchase.addItemDecoration(
            DividerItemDecoration(
                context, DividerItemDecoration.VERTICAL
            )
        )

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

        purchaseViewModel.addToCartResponse.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        if (purchaseViewModel.isDialogShowing) {
                            loadingDialog.hideDialog()
                        }
                        this.findNavController()
                            .navigate(PurchaseMenuFragmentDirections.actionPurchaseMenuFragmentToCartFragment())
                        purchaseViewModel.doneNavigateToCart()
                    }
                    is Resource.Loading -> {
                        if (!purchaseViewModel.isDialogShowing) {
                            purchaseViewModel.doneShowingDialog()
                            loadingDialog.showDialog()
                        }
                    }
                    is Resource.Error -> {
                        loadingDialog.hideDialog()
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