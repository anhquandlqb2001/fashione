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
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.databinding.FragmentBottomSheetProductVariantBinding
import vn.quanprolazer.fashione.viewmodels.BottomSheetProductVariantViewModel


class BottomSheetProductVariantFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetProductVariantBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetProductVariantBinding.inflate(inflater, container, false)


        val variants = BottomSheetProductVariantFragmentArgs.fromBundle(requireArguments()).variants

        val viewModel = ViewModelProvider(this, BottomSheetProductVariantViewModel.Factory(variants.toList()))[BottomSheetProductVariantViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        viewModel.productVariants.observe(viewLifecycleOwner, {
            it?.let {
                val colorChipGroup = binding.cgColors
                val colorInflater = LayoutInflater.from(colorChipGroup.context)

                val children = it.map { variant ->
                    val chip = colorInflater.inflate(R.layout.list_item_chip, colorChipGroup, false) as Chip
                    chip.text = variant.color
                    chip.tag = variant.color
                    chip.setOnCheckedChangeListener { buttonView, isChecked ->

                    }
                    chip
                }
                colorChipGroup.removeAllViews()

                for (chip in children) {
                    colorChipGroup.addView(chip)
                }
            }
        })

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}