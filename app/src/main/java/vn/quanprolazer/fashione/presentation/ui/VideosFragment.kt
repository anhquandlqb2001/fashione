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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import vn.quanprolazer.fashione.databinding.FragmentVideosBinding
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.presentation.adapters.VideoItemAdapter
import vn.quanprolazer.fashione.presentation.adapters.VideoItemListener
import vn.quanprolazer.fashione.presentation.utilities.LoadingDialog
import vn.quanprolazer.fashione.presentation.utilities.MarginItemDecoration
import vn.quanprolazer.fashione.presentation.viewmodels.VideoViewModel

@AndroidEntryPoint
class VideosFragment : Fragment() {

    private var _binding: FragmentVideosBinding? = null

    /** This property is only valid between onCreateView and onDestroyView. */
    private val binding get() = _binding!!

    private val viewModel: VideoViewModel by viewModels()

    private val videoAdapter: VideoItemAdapter by lazy {
        VideoItemAdapter(object : VideoItemListener() {
            override fun onClick(uri: String) {
                viewModel.onClickVideo(uri)
            }
        })
    }

    private val loadingDialog: LoadingDialog by lazy { LoadingDialog(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideosBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.rvVideos.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        binding.rvVideos.adapter = videoAdapter
        binding.rvVideos.addItemDecoration(MarginItemDecoration(20))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.liveVideos.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        videoAdapter.submitList(it.data)
                        loadingDialog.hideDialog()
                    }
                    is Resource.Loading -> {
                        loadingDialog.showDialog()
                    }
                    is Resource.Error -> Timber.e(it.exception)
                }
            }
        })

        viewModel.navigateToVideo.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController()
                    .navigate(VideosFragmentDirections.actionVideosFragmentToLiveVideoFragment(it))
                viewModel.doneNavigateToVideo()
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}