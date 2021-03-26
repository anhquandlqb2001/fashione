package vn.quanprolazer.fashione.ui.home

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate layout
        binding = FragmentHomeBinding.inflate(inflater)

        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // Category Section
        val categoryAdapter = CategoryAdapter()
        binding.rvCategory.adapter = categoryAdapter
        homeViewModel.categories.observe(viewLifecycleOwner, Observer {
            it?.let {
                categoryAdapter.submitList(it)
            }
        })
        // End Category Section

        // Product Featured Section
        val productFeaturedAdapter = ProductAdapter()
        binding.rvFeatured.adapter = productFeaturedAdapter
        homeViewModel.products.observe(viewLifecycleOwner, Observer {
            it?.let {
                productFeaturedAdapter.submitList(it)
            }
        })
        // End Product Featured Section


        // Product Best Sell Section
        val productBestSellAdapter = ProductAdapter()
        binding.rvBestSell.adapter = productBestSellAdapter
        homeViewModel.products.observe(viewLifecycleOwner, Observer {
            it?.let {
                productBestSellAdapter.submitList(it)
            }
        })
        // End Product Best Sell Section

        // Product Suggest Section
        val productSuggestAdapter = ProductAdapter()
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