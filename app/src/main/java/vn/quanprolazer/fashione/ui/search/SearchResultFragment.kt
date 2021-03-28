package vn.quanprolazer.fashione.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.databinding.FragmentSearchResultBinding
import vn.quanprolazer.fashione.ui.product.ProductAdapter

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

        val productResultAdapter = ProductAdapter()

        binding.lifecycleOwner = viewLifecycleOwner

        binding.rvSearchResult.adapter = productResultAdapter

        viewModel.products.observe(viewLifecycleOwner, {
            it?.let {
                productResultAdapter.submitList(it)
            }
        })

        val productResultLayoutManager = GridLayoutManager(context, 2)
        binding.rvSearchResult.layoutManager = productResultLayoutManager




        return binding.root
    }


}