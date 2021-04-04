/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.adapters.CategoryAdapter
import vn.quanprolazer.fashione.adapters.OnClickCategoryListener
import vn.quanprolazer.fashione.databinding.FragmentHomeBinding
import vn.quanprolazer.fashione.adapters.OnClickListener
import vn.quanprolazer.fashione.adapters.ProductAdapter
import vn.quanprolazer.fashione.utilities.onDone
import vn.quanprolazer.fashione.viewmodels.HomeViewModel
import vn.quanprolazer.fashione.viewmodels.SearchViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate layout
        binding = FragmentHomeBinding.inflate(inflater)

        binding.lifecycleOwner = viewLifecycleOwner

        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // Category Section
        val categoryAdapter = CategoryAdapter(OnClickCategoryListener {
            homeViewModel.onClickCategory(it)
        })

        binding.rvCategory.adapter = categoryAdapter
        homeViewModel.categories.observe(viewLifecycleOwner, Observer {
            it?.let {
                categoryAdapter.submitList(it)
            }
        })
        // End Category Section

        // Product Featured Section
        val productFeaturedAdapter = ProductAdapter(OnClickListener {
            homeViewModel.onClickProduct(it)
        })

        binding.rvFeatured.adapter = productFeaturedAdapter
        homeViewModel.products.observe(viewLifecycleOwner, Observer {
            it?.let {
                productFeaturedAdapter.submitList(it)
            }
        })
        // End Product Featured Section


        // Product Best Sell Section
        val productBestSellAdapter = ProductAdapter(OnClickListener {
            homeViewModel.onClickProduct(it)
        })
        binding.rvBestSell.adapter = productBestSellAdapter
        homeViewModel.products.observe(viewLifecycleOwner, Observer {
            it?.let {
                productBestSellAdapter.submitList(it)
            }
        })
        // End Product Best Sell Section

        // Product Suggest Section
        val productSuggestAdapter = ProductAdapter(OnClickListener {
            homeViewModel.onClickProduct(it)
        })
        binding.rvSuggestProduct.adapter = productBestSellAdapter
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
        // End Product Suggest Section


        // Product direction

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

        // End  Product direction



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


        // Search on done event
        binding.etSearch.onDone {
            homeViewModel.onSearch()
        }

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

        val searchViewModel = SearchViewModel(homeViewModel)

        binding.searchViewModel = searchViewModel

        homeViewModel.exception.observe(viewLifecycleOwner, {
            it?.let {
                Snackbar.make(binding.root, "Network error", Snackbar.LENGTH_INDEFINITE).show()
            }
        })
        return binding.root
    }
}


class MarginItemDecoration(
    private val spaceSize: Int,
    private val spanCount: Int = 1,
    private val orientation: Int = GridLayoutManager.VERTICAL
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            if (orientation == GridLayoutManager.VERTICAL) {
                if (parent.getChildAdapterPosition(view) < spanCount) {
                    bottom = spaceSize
                }
                if (parent.getChildAdapterPosition(view) % spanCount == 0) {
                    right = spaceSize
                }
            } else {
                if (parent.getChildAdapterPosition(view) < spanCount) {
                    right = spaceSize
                }
                if (parent.getChildAdapterPosition(view) % spanCount == 0) {
                    bottom = spaceSize
                }
            }

            top = spaceSize
            left = spaceSize
        }
    }
}

