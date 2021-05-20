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
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import vn.quanprolazer.fashione.databinding.FragmentReviewBinding
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.presentation.adapters.ReviewItemAdapter
import vn.quanprolazer.fashione.presentation.utilities.ViewDialog
import vn.quanprolazer.fashione.presentation.viewmodels.ReviewViewModel
import javax.inject.Inject


@AndroidEntryPoint
class ReviewFragment : Fragment() {

    private var _binding: FragmentReviewBinding? = null
    private val binding get() = _binding!!

    private val reviewItemAdapter: ReviewItemAdapter by lazy {
        ReviewItemAdapter()
    }

    @Inject
    lateinit var reviewViewModelFactory: ReviewViewModel.AssistedFactory
    private val reviewViewModel: ReviewViewModel by viewModels {
        ReviewViewModel.provideFactory(
            reviewViewModelFactory,
            ReviewFragmentArgs.fromBundle(requireArguments()).productId
        )
    }

    private val loadingDialog: ViewDialog by lazy { ViewDialog(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReviewBinding.inflate(inflater, container, false)

        binding.rvReview.adapter = reviewItemAdapter
        binding.rvReview.isNestedScrollingEnabled = false
        binding.rvReview.addItemDecoration(
            DividerItemDecoration(
                context, DividerItemDecoration.VERTICAL
            )
        )
        binding.rvReview.isNestedScrollingEnabled = false

        binding.nsvReview.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight && !(reviewViewModel.lastVisibleId.isNullOrBlank())) {
                binding.cpiLoading.visibility = View.VISIBLE
                reviewViewModel.fetchMoreReview()
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reviewViewModel.reviewWithRatings.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        if (it.data.isEmpty()) {
                            binding.rvReview.visibility = View.GONE
                        } else {
                            loadingDialog.hideDialog()
                            binding.rvReview.visibility = View.VISIBLE
                            reviewItemAdapter.submitList(it.data)
                            binding.cpiLoading.visibility = View.GONE
                        }
                    }
                    is Resource.Loading -> {
                        loadingDialog.showDialog()
                    }
                    is Resource.Error -> {
                        binding.rvReview.visibility = View.GONE
                        Timber.e(it.exception)
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

