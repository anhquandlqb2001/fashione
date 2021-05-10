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
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.adapters.PurchaseFragmentAdapter
import vn.quanprolazer.fashione.databinding.FragmentAddPickupAddressBinding
import vn.quanprolazer.fashione.databinding.FragmentPurchaseMenuBinding

class PurchaseMenuFragment : Fragment() {

    private var _binding: FragmentPurchaseMenuBinding? = null

    private val binding: FragmentPurchaseMenuBinding get() = _binding!!

    private val purchaseFragmentAdapter: PurchaseFragmentAdapter by lazy {
        PurchaseFragmentAdapter(this)
    }

    private lateinit var viewPager: ViewPager2

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View { // Inflate the layout for this fragment
        _binding = FragmentPurchaseMenuBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = binding.vp2PurchaseMenu
        viewPager.adapter = purchaseFragmentAdapter

        val tabLayout = binding.tablayoutPurchaseMenu
        TabLayoutMediator(tabLayout, viewPager) { tab, position -> //            tab.text
            when(position) {
                0 -> tab.text = "Chờ xác nhận"
                1 -> tab.text = "Đang giao"
            }
        }.attach()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}