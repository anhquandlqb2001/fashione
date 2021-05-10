/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import vn.quanprolazer.fashione.ui.ConfirmingFragment
import vn.quanprolazer.fashione.ui.DeliveredFragment

class PurchaseFragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ConfirmingFragment()
            1 -> DeliveredFragment()
            else -> ConfirmingFragment()
        }
    }
}