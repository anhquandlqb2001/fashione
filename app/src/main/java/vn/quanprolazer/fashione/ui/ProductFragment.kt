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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.adapters.ProductImageAdapter
import vn.quanprolazer.fashione.databinding.FragmentProductDetailBinding
import vn.quanprolazer.fashione.viewmodels.ProductViewModel


class ProductFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProductDetailBinding.inflate(inflater)

        val product = ProductFragmentArgs.fromBundle(requireArguments()).product

        val viewModel = ViewModelProvider(
            this,
            ProductViewModel.Factory(product)
        )[ProductViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel


        // section for setup images slider
        val productImageAdapter = ProductImageAdapter()
        binding.rvProductImage.adapter = productImageAdapter

        viewModel.productDetail.observe(viewLifecycleOwner, {
            productImageAdapter.submitList(it.images)
        })
        // end section

        snapHelper.attachToRecyclerView(binding.rvProductImage)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvProductImage.layoutManager = layoutManager


        // bottom sheet

        binding.btnBuy.setOnClickListener {
            val view: View = layoutInflater.inflate(R.layout.fragment_modal_bottom_sheet_product_variant, null)
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(view)
            dialog.show()
        }
        // end section


        return binding.root

    }

//    private fun setupBottomSheet() {
//        val bottomSheetBehavior = BottomSheetBehavior.from(binding.flStandardBottomSheet)
//        bottomSheetBehavior.addBottomSheetCallback(object :
//            BottomSheetBehavior.BottomSheetCallback() {
//            /**
//             * Called when the bottom sheet changes its state.
//             *
//             * @param bottomSheet The bottom sheet view.
//             * @param newState The new state. This will be one of [.STATE_DRAGGING], [     ][.STATE_SETTLING], [.STATE_EXPANDED], [.STATE_COLLAPSED], [     ][.STATE_HIDDEN], or [.STATE_HALF_EXPANDED].
//             */
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//
//            }
//
//            /**
//             * Called when the bottom sheet is being dragged.
//             *
//             * @param bottomSheet The bottom sheet view.
//             * @param slideOffset The new offset of this bottom sheet within [-1,1] range. Offset increases
//             * as this bottom sheet is moving upward. From 0 to 1 the sheet is between collapsed and
//             * expanded states and from -1 to 0 it is between hidden and collapsed states.
//             */
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//
//            }
//        })
//        binding.btnBuy.setOnClickListener {
//            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
//                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//                it.visibility = View.VISIBLE
//            } else {
//                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
//                it.visibility = View.GONE
//            }
//        }
//    }

    /**
     * Snap scroll product images
     */
    private val snapHelper: LinearSnapHelper = object : LinearSnapHelper() {
        override fun findTargetSnapPosition(
            layoutManager: RecyclerView.LayoutManager,
            velocityX: Int,
            velocityY: Int
        ): Int {
            val centerView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
            val position = layoutManager.getPosition(centerView)
            var targetPosition = -1
            if (layoutManager.canScrollHorizontally()) {
                targetPosition = if (velocityX < 0) {
                    position - 1
                } else {
                    position + 1
                }
            }
            if (layoutManager.canScrollVertically()) {
                targetPosition = if (velocityY < 0) {
                    position - 1
                } else {
                    position + 1
                }
            }
            val firstItem = 0
            val lastItem = layoutManager.itemCount - 1
            targetPosition = Math.min(lastItem, Math.max(targetPosition, firstItem))
            return targetPosition
        }
    }

    /**
     * Called when the fragment is no longer in use.  This is called
     * after [.onStop] and before [.onDetach].
     */
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}