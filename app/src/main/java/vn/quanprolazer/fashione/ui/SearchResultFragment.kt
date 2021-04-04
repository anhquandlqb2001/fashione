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
import androidx.recyclerview.widget.GridLayoutManager
import vn.quanprolazer.fashione.databinding.FragmentSearchResultBinding
import vn.quanprolazer.fashione.adapters.OnClickListener
import vn.quanprolazer.fashione.adapters.ProductAdapter
import vn.quanprolazer.fashione.viewmodels.SearchResultViewModel
import vn.quanprolazer.fashione.viewmodels.SearchResultViewModelFactory

class SearchResultFragment : Fragment() {


    private lateinit var binding: FragmentSearchResultBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchResultBinding.inflate(inflater)

        val category = arguments?.let { SearchResultFragmentArgs.fromBundle(it).category }
        val query = arguments?.let { SearchResultFragmentArgs.fromBundle(it).query }

        val modelFactory = SearchResultViewModelFactory(category, query)

        val viewModel = ViewModelProvider(this, modelFactory)[SearchResultViewModel::class.java]

        binding.viewModel = viewModel

        val productResultAdapter = ProductAdapter(OnClickListener {
            viewModel.onClickProduct(it)
        })

        binding.lifecycleOwner = viewLifecycleOwner

        binding.rvSearchResult.adapter = productResultAdapter

        viewModel.products.observe(viewLifecycleOwner, {
            it?.let {
                productResultAdapter.submitList(it)
            }
        })

        // navigate to product detail screen
        viewModel.navigateToProductDetail.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController().navigate(
                    SearchResultFragmentDirections.actionSearchResultFragmentToProductFragment(
                        it
                    )
                )
                viewModel.doneNavigate()
            }
        })
        // end section

        val productResultLayoutManager = GridLayoutManager(context, 2)
        binding.rvSearchResult.layoutManager = productResultLayoutManager

        return binding.root
    }


}