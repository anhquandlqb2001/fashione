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
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import vn.quanprolazer.fashione.databinding.FragmentPurchaseMenuBinding
import vn.quanprolazer.fashione.domain.models.OrderItemStatusType
import vn.quanprolazer.fashione.presentation.adapters.PurchaseFragmentAdapter
import vn.quanprolazer.fashione.presentation.viewmodels.PurchaseViewModel


const val CONFIRMING_POSITION = 0
const val COLLECTING_POSITION = 1
const val DELIVERING_POSITION = 2
const val DELIVERED_POSITION = 3
const val COMPLETE_POSITION = 4

@AndroidEntryPoint
class PurchaseMenuFragment : Fragment() {

    private var _binding: FragmentPurchaseMenuBinding? = null

    private val binding: FragmentPurchaseMenuBinding get() = _binding!!

    private val purchaseViewModel: PurchaseViewModel by activityViewModels()

    private val purchaseArgs: PurchaseMenuFragmentArgs by lazy {
        PurchaseMenuFragmentArgs.fromBundle(requireArguments())
    }

    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View { // Inflate the layout for this fragment
        _binding = FragmentPurchaseMenuBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        viewPager = binding.vp2PurchaseMenu
        viewPager.adapter = PurchaseFragmentAdapter(this)

        val selectedPage = purchaseArgs.selectedTab
        viewPager.setCurrentItem(selectedPage, true);

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = binding.tablayoutPurchaseMenu

        setupTabTitle(tabLayout)

        purchaseViewModel.updatePurchaseItems(purchaseArgs.selectedTab)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    when (tab.position) {
                        CONFIRMING_POSITION -> {
                            purchaseViewModel.updatePurchaseItems(CONFIRMING_POSITION)
                        }
                        COLLECTING_POSITION -> {
                            purchaseViewModel.updatePurchaseItems(COLLECTING_POSITION)
                        }
                        DELIVERING_POSITION -> {
                            purchaseViewModel.updatePurchaseItems(DELIVERING_POSITION)
                        }
                        DELIVERED_POSITION -> {
                            purchaseViewModel.updatePurchaseItems(DELIVERED_POSITION)
                        }
                        COMPLETE_POSITION -> {
                            purchaseViewModel.updatePurchaseItems(COMPLETE_POSITION)
                        }
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

    private fun setupTabTitle(tabLayout: TabLayout) {
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                CONFIRMING_POSITION -> {
                    tab.text = OrderItemStatusType.CONFIRMING.status
                }
                COLLECTING_POSITION -> {
                    tab.text = OrderItemStatusType.COLLECTING.status
                }
                DELIVERING_POSITION -> {
                    tab.text = OrderItemStatusType.DELIVERING.status
                }
                DELIVERED_POSITION -> {
                    tab.text = OrderItemStatusType.DELIVERED.status
                }
                COMPLETE_POSITION -> {
                    tab.text = OrderItemStatusType.COMPLETE.status
                }
            }
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}