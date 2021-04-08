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
import androidx.lifecycle.ViewModelProvider
import timber.log.Timber
import vn.quanprolazer.fashione.adapters.ChipAdapter
import vn.quanprolazer.fashione.adapters.OnClickChipListener
import vn.quanprolazer.fashione.databinding.FragmentModalBottomSheetProductVariantBinding
import vn.quanprolazer.fashione.viewmodels.BottomSheetProductVariantViewModel
import vn.quanprolazer.fashione.viewmodels.ProductViewModel

class BottomSheetProductVariantFragment : Fragment() {

    private var _binding: FragmentModalBottomSheetProductVariantBinding? = null

    private val binding get() = _binding!!

    private val productViewModel: ProductViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentModalBottomSheetProductVariantBinding.inflate(inflater, container, false)

        Timber.i(productViewModel.productDetail.toString())

        val viewModel = ViewModelProvider(this, BottomSheetProductVariantViewModel.Factory(productViewModel.productDetail))[BottomSheetProductVariantViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = ChipAdapter(OnClickChipListener {

        })

        binding.rvSize.adapter = adapter

        viewModel.productDetail.observe(viewLifecycleOwner, {
            adapter.submitList(it.variants)
        })


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}