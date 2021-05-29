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
import androidx.navigation.fragment.findNavController
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.material.snackbar.Snackbar
import vn.quanprolazer.fashione.databinding.FragmentVideoPlayerBinding
import vn.quanprolazer.fashione.presentation.utilities.LoadingDialog

class LiveVideoFragment : Fragment() {

    private var _binding: FragmentVideoPlayerBinding? = null
    /** This property is only valid between onCreateView and onDestroyView. */
    private val binding get() = _binding!!

    private val uri: String by lazy { LiveVideoFragmentArgs.fromBundle(requireArguments()).uri }

    private var _player: SimpleExoPlayer? = null
    private val player get() = _player!!

    private val loadingDialog: LoadingDialog by lazy { LoadingDialog(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoPlayerBinding.inflate(inflater, container, false)

        _player = SimpleExoPlayer.Builder(requireContext()).build()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Create a data source factory.
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        // Create a HLS media source pointing to a playlist uri.
        val hlsMediaSource: HlsMediaSource = HlsMediaSource.Factory(dataSourceFactory)
            .setAllowChunklessPreparation(true)
            .createMediaSource(MediaItem.fromUri(uri))

        player.setMediaSource(hlsMediaSource)

        player.addListener(object : Player.Listener {
            override fun onPlayerError(error: ExoPlaybackException) {
                when (error.type) {
                    ExoPlaybackException.TYPE_SOURCE -> {
                        Snackbar.make(binding.root, "Video đã ngừng phát", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                    else -> {
                        Snackbar.make(binding.root, "Video đã ngừng phát", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
                findNavController().popBackStack()
                loadingDialog.hideDialog()
            }

            override fun onIsLoadingChanged(isLoading: Boolean) {
                super.onIsLoadingChanged(isLoading)
                if (isLoading) {
                    loadingDialog.showDialog()
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                if (isPlaying) {
                    loadingDialog.hideDialog()
                }
            }
        })

        val playerView = binding.pvVideo
        playerView.player = player
        player.prepare()
        player.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
        _binding = null
        _player = null
    }

}