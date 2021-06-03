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
import dagger.hilt.android.AndroidEntryPoint
import vn.quanprolazer.fashione.databinding.FragmentSearchResultBinding
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.presentation.adapters.ProductAdapter
import vn.quanprolazer.fashione.presentation.viewmodels.SearchResultViewModel
import javax.inject.Inject

@AndroidEntryPoint
class SearchResultFragment : Fragment() {

    private var _binding: FragmentSearchResultBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: SearchResultViewModel.AssistedFactory
    private val viewModel: SearchResultViewModel by viewModels {
        val args = SearchResultFragmentArgs.fromBundle(requireArguments())
        SearchResultViewModel.provideFactory(viewModelFactory, args.category, args.query)
    }

    private val productResultAdapter: ProductAdapter by lazy {
        ProductAdapter(ProductAdapter.OnClickListener {
            viewModel.onClickProduct(it)
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater,
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
                    is Resource.Success -> productResultAdapter.submitList(it.data)
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