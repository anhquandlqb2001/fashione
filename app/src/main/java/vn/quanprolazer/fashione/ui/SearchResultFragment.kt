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
import dagger.hilt.android.AndroidEntryPoint
import vn.quanprolazer.fashione.databinding.FragmentSearchResultBinding
import vn.quanprolazer.fashione.adapters.OnClickListener
import vn.quanprolazer.fashione.adapters.ProductAdapter
import vn.quanprolazer.fashione.data.domain.model.Result
import vn.quanprolazer.fashione.viewmodels.SearchResultViewModel

@AndroidEntryPoint
class SearchResultFragment : Fragment() {

    private var _binding: FragmentSearchResultBinding? = null

    private val binding get() = _binding!!

    private val viewModel: SearchResultViewModel by lazy {
        val category = arguments?.let { SearchResultFragmentArgs.fromBundle(it).category }
        val query = arguments?.let { SearchResultFragmentArgs.fromBundle(it).query }

        ViewModelProvider(
            this, SearchResultViewModel.Factory(category, query)
        )[SearchResultViewModel::class.java]
    }

    private val productResultAdapter: ProductAdapter by lazy {
        ProductAdapter(OnClickListener {
            viewModel.onClickProduct(it)
        })
    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        binding.rvSearchResult.adapter = productResultAdapter
        val productResultLayoutManager = GridLayoutManager(context, 2)
        binding.rvSearchResult.layoutManager = productResultLayoutManager

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeProducts()
        observeNavigateToProductDetail()
    }

    private fun observeProducts() {
        viewModel.products.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Result.Success -> productResultAdapter.submitList(it.data)
                    else -> {
                    }
                }
            }
        })
    }

    private fun observeNavigateToProductDetail() {
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