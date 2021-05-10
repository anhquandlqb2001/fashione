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
import vn.quanprolazer.fashione.databinding.FragmentConfirmingBinding

class ConfirmingFragment : Fragment() {

    private var _binding: FragmentConfirmingBinding? = null

    private val binding: FragmentConfirmingBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? { // Inflate the layout for this fragment
        _binding = FragmentConfirmingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}