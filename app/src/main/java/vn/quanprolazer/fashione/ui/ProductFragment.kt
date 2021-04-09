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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import timber.log.Timber
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
        viewModel.navigateToBottomSheet.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController().navigate(ProductFragmentDirections.actionProductFragmentToBottomSheetProductVariantFragment(it.toTypedArray()))
                viewModel.doneNavigate()
            }
        })
        // end section


        return binding.root

    }


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