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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import vn.quanprolazer.fashione.adapters.ProductImageAdapter
import vn.quanprolazer.fashione.adapters.ReviewItemAdapter
import vn.quanprolazer.fashione.data.domain.model.Resource
import vn.quanprolazer.fashione.databinding.FragmentProductDetailBinding
import vn.quanprolazer.fashione.viewmodels.ProductSharedViewModel
import vn.quanprolazer.fashione.viewmodels.ProductViewModel
import javax.inject.Inject

@AndroidEntryPoint
class ProductFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null

    private val productImageAdapter: ProductImageAdapter by lazy {
        ProductImageAdapter()
    }


    private val binding get() = _binding!!

    @Inject
    lateinit var productViewModelFactory: ProductViewModel.AssistedFactory

    private val viewModel: ProductViewModel by viewModels {
        ProductViewModel.provideFactory(
            productViewModelFactory,
            ProductFragmentArgs.fromBundle(requireArguments()).product
        )
    }

    private val reviewItemAdapter: ReviewItemAdapter by lazy {
        ReviewItemAdapter()
    }

    private val sharedViewModel: ProductSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.rvProductImage.adapter = productImageAdapter
        binding.rvReview.adapter = reviewItemAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        observeProductImages(productImageAdapter)

        // snap scroll image slider
        snapHelper.attachToRecyclerView(binding.rvProductImage)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvProductImage.layoutManager = layoutManager

        observeNavigateToBottomSheet()
        observeException()
        observeSuccess()

        viewModel.overviewRating.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        binding.ratingBar2.rating = it.data.averageRate
                        binding.tvCountRate.text =
                            "${it.data.averageRate}/5 (${it.data.countRate} đánh giá)"
                    }
                    is Resource.Loading -> {
                    }
                }
            }
        })

        viewModel.reviewWithRatings.observe(viewLifecycleOwner, {
            Timber.i(it.toString())
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        reviewItemAdapter.submitList(it.data)
                    }
                }
            }
        })

    }

    private fun observeSuccess() {
        sharedViewModel.successMessage.observe(viewLifecycleOwner, {
            it?.let {
                makeSnackBar(it)
                sharedViewModel.doneObserve()
            }
        })
    }

    private fun observeException() {
        sharedViewModel.exceptionMessage.observe(viewLifecycleOwner, {
            it?.let {
                makeSnackBar(it)
                sharedViewModel.doneObserve()
            }
        })
    }

    private fun makeSnackBar(text: String, duration: Int = Snackbar.LENGTH_SHORT) =
        Snackbar.make(binding.root, text, duration).show()


    private fun observeNavigateToBottomSheet() {
        viewModel.navigateToBottomSheet.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController().navigate(
                    ProductFragmentDirections.actionProductFragmentToBottomSheetProductVariantFragment(
                        it
                    )
                )
                viewModel.doneNavigate()
            }
        })
    }

    private fun observeProductImages(productImageAdapter: ProductImageAdapter) {
        viewModel.productImages.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> productImageAdapter.submitList(it.data)
                is Resource.Loading -> {
                }
                is Resource.Error -> {
                }
            }

        })
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
            targetPosition = lastItem.coerceAtMost(targetPosition.coerceAtLeast(firstItem))
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