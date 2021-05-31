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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.databinding.FragmentHomeBinding
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.presentation.adapters.CategoryAdapter
import vn.quanprolazer.fashione.presentation.adapters.OnClickCategoryListener
import vn.quanprolazer.fashione.presentation.adapters.OnClickListener
import vn.quanprolazer.fashione.presentation.adapters.ProductAdapter
import vn.quanprolazer.fashione.presentation.utilities.MarginItemDecoration
import vn.quanprolazer.fashione.presentation.utilities.onDone
import vn.quanprolazer.fashione.presentation.viewmodels.HomeViewModel


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    /** This property is only valid between onCreateView and onDestroyView. */
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private val categoryAdapter by lazy {
        CategoryAdapter(OnClickCategoryListener {
            viewModel.onClickCategory(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate layout
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        context ?: binding.root

        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner

        binding.rvCategory.adapter = categoryAdapter

        binding.viewModel = viewModel

        return binding.root
    }

    /**
     * Called immediately after [.onCreateView]
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     * @param view The View returned by [.onCreateView].
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeCategory()

        // Product Featured Section
        setupProductFeaturedSection()
        // End Product Featured Section


        // Product Best Sell Section
        setupProductBestSellSection()
        // End Product Best Sell Section

        // Product Suggest Section
        setupProductSuggestSection()
        // End Product Suggest Section


        // Product direction
        observeNavigateToProductDetail()
        // End  Product direction


        observeNavigateToSearchResult()


        // Search on done event
        binding.etSearch.onDone {
            viewModel.onSearchByText()
        }

        setupSearchByTextNavigateEvent()

        handleException()
    }

    private fun observeCategory() {
        viewModel.categories.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Resource.Success -> categoryAdapter.submitList(it.data)
                    is Resource.Loading -> {
                    }
                    is Resource.Error ->
                        Timber.e(it.exception)

                }
            }
        })
    }

    private fun setupSearchByTextNavigateEvent() {
        viewModel.navigateToSearchResultByText.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToSearchResultFragment(
                        null,
                        it
                    )
                )
                viewModel.doneNavigate()
            }
        })
    }

    private fun handleException() {
        viewModel.exception.observe(viewLifecycleOwner, {
            it?.let {
                Snackbar.make(binding.root, "Network error", Snackbar.LENGTH_INDEFINITE).show()
            }
        })
    }

    private fun observeNavigateToSearchResult() {
        viewModel.navigateToSearchResultByCategory.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToSearchResultFragment(
                        it,
                        ""
                    )
                )
                viewModel.doneNavigate()
            }
        })
    }

    private fun observeNavigateToProductDetail() {
        viewModel.navigateToProductDetail.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToProductFragment(
                        it
                    )
                )
                viewModel.doneNavigate()
            }
        })
    }

    private fun setupProductSuggestSection() {
        val productSuggestAdapter = ProductAdapter(OnClickListener {
            viewModel.onClickProduct(it)
        })
        binding.rvSuggestProduct.adapter = productSuggestAdapter
        viewModel.products.observe(viewLifecycleOwner, {
            it?.let {
                productSuggestAdapter.submitList(it)
            }
        })

        binding.rvSuggestProduct.addItemDecoration(
            MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin), 2)
        )

        val productSuggestLayoutManager = GridLayoutManager(context, 2)
        binding.rvSuggestProduct.layoutManager = productSuggestLayoutManager
    }

    private fun setupProductBestSellSection() {
        val productBestSellAdapter = ProductAdapter(OnClickListener {
            viewModel.onClickProduct(it)
        })
        binding.rvBestSell.adapter = productBestSellAdapter
        viewModel.products.observe(viewLifecycleOwner, {
            it?.let {
                productBestSellAdapter.submitList(it)
            }
        })
    }

    private fun setupProductFeaturedSection() {
        val productFeaturedAdapter = ProductAdapter(OnClickListener {
            viewModel.onClickProduct(it)
        })

        binding.rvFeatured.adapter = productFeaturedAdapter
        viewModel.products.observe(viewLifecycleOwner, {
            it?.let {
                productFeaturedAdapter.submitList(it)
            }
        })
    }


    /**
     * Called when the view previously created by [.onCreateView] has
     * been detached from the fragment.  The next time the fragment needs
     * to be displayed, a new view will be created.  This is called
     * after [.onStop] and before [.onDestroy].  It is called
     * *regardless* of whether [.onCreateView] returned a
     * non-null view.  Internally it is called after the view's state has
     * been saved but before it has been removed from its parent.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




