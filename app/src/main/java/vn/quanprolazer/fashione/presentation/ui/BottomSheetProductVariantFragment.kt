/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.databinding.FragmentBottomSheetProductVariantBinding
import vn.quanprolazer.fashione.domain.models.ProductVariantOption
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.presentation.utilities.bindings.setProductVariantQty
import vn.quanprolazer.fashione.presentation.viewmodels.BottomSheetProductVariantViewModel
import vn.quanprolazer.fashione.presentation.viewmodels.ProductSharedViewModel
import javax.inject.Inject

@AndroidEntryPoint
class BottomSheetProductVariantFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetProductVariantBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var bottomSheetProductVariantViewModelFactory: BottomSheetProductVariantViewModel.AssistedFactory

    private val viewModel: BottomSheetProductVariantViewModel by viewModels {
        BottomSheetProductVariantViewModel.provideFactory(
            bottomSheetProductVariantViewModelFactory,
            BottomSheetProductVariantFragmentArgs.fromBundle(requireArguments()).product
        )
    }

    private val sharedViewModel: ProductSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetProductVariantBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeProductVariant()
        observeException()
        observeSuccess()
    }

    private fun observeSuccess() {
        viewModel.successMessage.observe(viewLifecycleOwner, {
            it?.let {
                dismissDialog()
                sharedViewModel.setSuccessMessage(it)
            }
        })
    }

    private fun observeException() {
        viewModel.exceptionMessage.observe(viewLifecycleOwner, {
            it?.let {
                dismissDialog()
                sharedViewModel.setExceptionMessage(it)
            }
        })
    }

    /**
     * Dismiss the fragment and its dialog
     */
    private fun dismissDialog() = this.dismiss()

    private fun observeProductVariant() {
        viewModel.productVariants.observe(viewLifecycleOwner, {
            it?.let {
                Timber.i(it.toString())

                viewModel.updateProductVariantOptions()

                when (it) {
                    is Resource.Success -> {
                        val colorChipGroup = binding.cgName
                        val colorInflater = LayoutInflater.from(colorChipGroup.context)
                        val children = it.data.map { variant ->
                            val chip = colorInflater.inflate(
                                R.layout.list_item_chip, colorChipGroup, false
                            ) as Chip
                            chip.text = variant.name
                            chip.tag = variant.name

                            chip.setOnCheckedChangeListener { _, isChecked ->
                                if (!isChecked) {
                                    return@setOnCheckedChangeListener
                                }

                                binding.tvVariantQty.setProductVariantQty(-1)
                                viewModel.resetOrderQty()
                                binding.llQtyControl.visibility = View.INVISIBLE



                                viewModel.onChangeVariantName(variant.name)

                                val optionChipGroup = binding.cgOption
                                val optionInflater = LayoutInflater.from(optionChipGroup.context)

                                when (variant.options) {
                                    is Resource.Success -> {
                                        val options =
                                            (variant.options as Resource.Success<List<ProductVariantOption>>).data.map { productVariantOption ->
                                                val optionChip = optionInflater.inflate(
                                                    R.layout.list_item_chip, optionChipGroup, false
                                                ) as Chip
                                                optionChip.text = productVariantOption.value
                                                optionChip.tag = productVariantOption.value
                                                optionChip.setOnCheckedChangeListener { _, isOptionChipChecked ->
                                                    if (!isOptionChipChecked) {
                                                        return@setOnCheckedChangeListener
                                                    }
                                                    binding.tvVariantQty.setProductVariantQty(
                                                        productVariantOption.quantity
                                                    )
                                                    viewModel.resetOrderQty()
                                                    viewModel.updateVariantPrice(
                                                        productVariantOption.price
                                                    )
                                                    viewModel.updateCartItemVariantId(
                                                        variant.id, productVariantOption.id
                                                    )
                                                    binding.llQtyControl.visibility = View.VISIBLE

                                                    viewModel.onChangeVariantValue(
                                                        productVariantOption.value
                                                    )
                                                    viewModel.setProductOrderValueLimit(
                                                        productVariantOption.quantity
                                                    )

                                                }
                                                optionChip
                                            }

                                        optionChipGroup.removeAllViews()
                                        for (option in options) {
                                            optionChipGroup.addView(option)
                                        }
                                    }
                                }


                            }
                            chip
                        }

                        colorChipGroup.removeAllViews()
                        for (chip in children) {
                            colorChipGroup.addView(chip)
                        }
                    }
                    else -> {
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