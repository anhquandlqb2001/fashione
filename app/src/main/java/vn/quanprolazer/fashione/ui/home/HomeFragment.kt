package vn.quanprolazer.fashione.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import vn.quanprolazer.fashione.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater)

        val categoryAdapter = CategoryAdapter()

        binding.rvCategory.adapter = categoryAdapter

        val productAdapter = ProductAdapter(arrayListOf())

        binding.rvFeatured.adapter = productAdapter

        return binding.root

    }

}