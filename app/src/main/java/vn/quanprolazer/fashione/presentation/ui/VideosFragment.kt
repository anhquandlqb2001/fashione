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
import androidx.recyclerview.widget.GridLayoutManager
import vn.quanprolazer.fashione.databinding.FragmentVideosBinding
import vn.quanprolazer.fashione.presentation.adapters.VideoItemAdapter
import vn.quanprolazer.fashione.presentation.adapters.VideoItemListener

class VideosFragment : Fragment() {

    private var _binding: FragmentVideosBinding? = null

    /** This property is only valid between onCreateView and onDestroyView. */
    private val binding get() = _binding!!

    private val videoAdapter: VideoItemAdapter by lazy {
        VideoItemAdapter(object : VideoItemListener() {
            override fun onClick(uri: String) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideosBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.rvVideos.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}