/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.databinding.FragmentDeliveredBinding
import vn.quanprolazer.fashione.databinding.FragmentPurchaseMenuBinding

class DeliveredFragment : Fragment() {

    private var _binding: FragmentDeliveredBinding? = null

    private val binding: FragmentDeliveredBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View { // Inflate the layout for this fragment
        _binding = FragmentDeliveredBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}