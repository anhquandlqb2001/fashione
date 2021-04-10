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
import timber.log.Timber
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
                val colorChipGroup = binding.cgName
                val colorInflater = LayoutInflater.from(colorChipGroup.context)

                val children = it.map { variant ->
                    val chip = colorInflater.inflate(R.layout.list_item_chip, colorChipGroup, false) as Chip
                    chip.text = variant.name
                    chip.tag = variant.name

                    chip.setOnCheckedChangeListener { buttonView, isChecked ->
                        if (!isChecked) {
                            return@setOnCheckedChangeListener
                        }
                        val optionChipGroup = binding.cgOption
                        val optionInflater = LayoutInflater.from(optionChipGroup.context)

                        val options = variant.options.map { productVariantOption ->
                            val optionChip = optionInflater.inflate(R.layout.list_item_chip, optionChipGroup, false) as Chip
                            optionChip.text = productVariantOption.value
                            optionChip.tag = productVariantOption.value
                            optionChip.setOnCheckedChangeListener { optionChipView, isChecked ->

                            }
                            optionChip
                        }

                        optionChipGroup.removeAllViews()
                        for (option in options) {
                            optionChipGroup.addView(option)
                        }

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