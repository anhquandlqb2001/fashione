package vn.quanprolazer.fashione.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import vn.quanprolazer.fashione.databinding.FragmentItemDetailBinding

class ProductFragment : Fragment() {

    private lateinit var binding: FragmentItemDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentItemDetailBinding.inflate(inflater)

        return binding.root

    }
}