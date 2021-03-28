package vn.quanprolazer.fashione.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import vn.quanprolazer.fashione.databinding.FragmentSearchResultBinding
import vn.quanprolazer.fashione.ui.product.ProductAdapter

class SearchResultFragment : Fragment() {


    private lateinit var binding : FragmentSearchResultBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchResultBinding.inflate(inflater)

        val categoryId = SearchResultFragmentArgs.fromBundle(requireArguments()).categoryId
        val query = SearchResultFragmentArgs.fromBundle(requireArguments()).query
        val modelFactory = SearchResultViewModelFactory(categoryId, query)

        val viewModel = ViewModelProvider(this, modelFactory)[SearchResultViewModel::class.java]

        binding.viewModel = viewModel

        val productResultAdapter = ProductAdapter()

        binding.rvSearchResult.adapter = productResultAdapter

        return binding.root
    }

}