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
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import timber.log.Timber
import vn.quanprolazer.fashione.databinding.FragmentLiveVideoBinding

class LiveVideoFragment : Fragment() {

    private var _binding: FragmentLiveVideoBinding? = null

    /** This property is only valid between onCreateView and onDestroyView. */
    private val binding get() = _binding!!

    private val uri: String by lazy { LiveVideoFragmentArgs.fromBundle(requireArguments()).uri }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLiveVideoBinding.inflate(inflater, container, false)

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

        val player = SimpleExoPlayer.Builder(requireContext()).build()
        player.setMediaSource(hlsMediaSource);

        player.addListener(object : Player.Listener {
            override fun onPlayerError(error: ExoPlaybackException) {
                Timber.e("Loi ne $error")
            }
        })

        val playerView = binding.pvVideo
        playerView.player = player
//        val mediaItem: MediaItem = MediaItem.fromUri(uri)
//        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}