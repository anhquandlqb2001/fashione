package vn.quanprolazer.fashione.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import vn.quanprolazer.fashione.databinding.FragmentProductDetailBinding

class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProductDetailBinding.inflate(inflater)

        val product = ProductFragmentArgs.fromBundle(requireArguments()).product

        val modelFactory = ProductViewModelFactory(product)

        val viewModel = ViewModelProvider(this, modelFactory)[ProductViewModel::class.java]

        val productImageAdapter = ProductImageAdapter()

        binding.rvProductImage.adapter = productImageAdapter


        return binding.root

    }
}