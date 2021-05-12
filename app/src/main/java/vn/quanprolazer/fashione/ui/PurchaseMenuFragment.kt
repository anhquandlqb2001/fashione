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
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import vn.quanprolazer.fashione.adapters.PurchaseFragmentAdapter
import vn.quanprolazer.fashione.databinding.FragmentPurchaseMenuBinding
import vn.quanprolazer.fashione.viewmodels.PurchaseViewModel

const val CONFIRMING_POSITION = 0
const val DELIVERING_POSITION = 1
const val DELIVERED_POSITION = 2

@AndroidEntryPoint
class PurchaseMenuFragment : Fragment() {

    private var _binding: FragmentPurchaseMenuBinding? = null

    private val binding: FragmentPurchaseMenuBinding get() = _binding!!

    private val purchaseFragmentAdapter: PurchaseFragmentAdapter by lazy {
        PurchaseFragmentAdapter(this)
    }

    private val purchaseViewModel: PurchaseViewModel by activityViewModels()

    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View { // Inflate the layout for this fragment
        _binding = FragmentPurchaseMenuBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = binding.vp2PurchaseMenu
        viewPager.adapter = purchaseFragmentAdapter

        val tabLayout = binding.tablayoutPurchaseMenu
        TabLayoutMediator(tabLayout, viewPager) { tab, position -> //            tab.text
            when (position) {
                CONFIRMING_POSITION -> {
                    tab.text = "Chờ xác nhận"
                }
                DELIVERING_POSITION -> {
                    tab.text = "Đang giao"
                }
                DELIVERED_POSITION -> {
                    tab.text = "Hoàn thành"
                }
            }
        }.attach()
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    when (tab.position) {
                        CONFIRMING_POSITION -> purchaseViewModel.updatePurchaseItems(
                            CONFIRMING_POSITION
                        )
                        DELIVERING_POSITION -> purchaseViewModel.updatePurchaseItems(
                            DELIVERING_POSITION
                        )
                        DELIVERED_POSITION -> purchaseViewModel.updatePurchaseItems(
                            DELIVERED_POSITION
                        )
                        else -> {
                            purchaseViewModel.updatePurchaseItems(CONFIRMING_POSITION)
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}