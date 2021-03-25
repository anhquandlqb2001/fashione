package vn.quanprolazer.fashione.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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

        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val categoryAdapter = CategoryAdapter()

        binding.rvCategory.adapter = categoryAdapter

        val productAdapter = ProductAdapter(arrayListOf())

        binding.rvFeatured.adapter = productAdapter

        homeViewModel.categories.observe(viewLifecycleOwner, Observer {
            Log.i("HomeFragment", it.toString())
        })

        return binding.root

    }

}