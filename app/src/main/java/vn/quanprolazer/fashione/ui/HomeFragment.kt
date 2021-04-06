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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.adapters.CategoryAdapter
import vn.quanprolazer.fashione.adapters.OnClickCategoryListener
import vn.quanprolazer.fashione.databinding.FragmentHomeBinding
import vn.quanprolazer.fashione.adapters.OnClickListener
import vn.quanprolazer.fashione.adapters.ProductAdapter
import vn.quanprolazer.fashione.utilities.MarginItemDecoration
import vn.quanprolazer.fashione.utilities.onDone
import vn.quanprolazer.fashione.viewmodels.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    /** This property is only valid between onCreateView and onDestroyView. */
    private val binding get() = _binding!!

    /**
     * One way to delay creation of the viewModel until an appropriate lifecycle method is to use
     * lazy. This requires that viewModel not be referenced before onActivityCreated, which we
     * do in this Fragment.
     */
    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    private var categoryAdapter: CategoryAdapter? = null

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
        homeViewModel.categories.observe(viewLifecycleOwner, {
            it?.apply {
                categoryAdapter?.submitList(it)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate layout
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = homeViewModel

        val searchViewModel = HomeViewModel.SearchViewModel(homeViewModel)
        binding.searchViewModel = searchViewModel


        // Category Section
        setupCategorySection()
        // End Category Section

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
        setupProductNavigateEvent()
        // End  Product direction


        setupSearchByCategoryNavigateEvent()


        // Search on done event
        binding.etSearch.onDone {
            homeViewModel.onSearch()
        }

        setupSearchByTextNavigateEvent()

        handleException()

        return binding.root
    }

    private fun handleException() {
        homeViewModel.exception.observe(viewLifecycleOwner, {
            it?.let {
                Snackbar.make(binding.root, "Network error", Snackbar.LENGTH_INDEFINITE).show()
            }
        })
    }

    private fun setupSearchByTextNavigateEvent() {
        homeViewModel.navigateToSearchResultByText.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToSearchResultFragment(
                        null,
                        it
                    )
                )
                homeViewModel.doneNavigate()
            }
        })
    }

    private fun setupSearchByCategoryNavigateEvent() {
        homeViewModel.navigateToSearchResult.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToSearchResultFragment(
                        it,
                        ""
                    )
                )
                homeViewModel.doneNavigate()
            }
        })
    }

    private fun setupProductNavigateEvent() {
        homeViewModel.navigateToProductDetail.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToProductFragment(
                        it
                    )
                )
                homeViewModel.doneNavigate()
            }
        })
    }

    private fun setupProductSuggestSection() {
        val productSuggestAdapter = ProductAdapter(OnClickListener {
            homeViewModel.onClickProduct(it)
        })
        binding.rvSuggestProduct.adapter = productSuggestAdapter
        homeViewModel.products.observe(viewLifecycleOwner, Observer {
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
            homeViewModel.onClickProduct(it)
        })
        binding.rvBestSell.adapter = productBestSellAdapter
        homeViewModel.products.observe(viewLifecycleOwner, Observer {
            it?.let {
                productBestSellAdapter.submitList(it)
            }
        })
    }

    private fun setupProductFeaturedSection() {
        val productFeaturedAdapter = ProductAdapter(OnClickListener {
            homeViewModel.onClickProduct(it)
        })

        binding.rvFeatured.adapter = productFeaturedAdapter
        homeViewModel.products.observe(viewLifecycleOwner, Observer {
            it?.let {
                productFeaturedAdapter.submitList(it)
            }
        })
    }

    private fun setupCategorySection() {
        val categoryAdapter = CategoryAdapter(OnClickCategoryListener {
            homeViewModel.onClickCategory(it)
        })

        binding.rvCategory.adapter = categoryAdapter
        homeViewModel.categories.observe(viewLifecycleOwner, Observer {
            it?.let {
                categoryAdapter.submitList(it)
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




