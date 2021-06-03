/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import vn.quanprolazer.fashione.databinding.FragmentMessageBinding
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.presentation.adapters.MessageAdapter
import vn.quanprolazer.fashione.presentation.viewmodels.MessageViewModel

@AndroidEntryPoint
class MessageFragment : Fragment() {

    private var _binding: FragmentMessageBinding? = null
    private val binding: FragmentMessageBinding get() = _binding!!

    private val adapter: MessageAdapter by lazy { MessageAdapter() }

    private val viewModel: MessageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        binding.recyclerGchat.adapter = adapter
        binding.recyclerGchat.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            fetchMessage()
        }

        viewModel.messages.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        adapter.bindMessages(it.data)
                        scrollToBottom(it.data.size - 1)
                        viewModel.observeRecentlyIncomingMessage()
                    }
                    is Resource.Loading -> {
                    }
                    is Resource.Error -> Timber.e(it.exception)
                }
            }
        })

        viewModel.status.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        binding.etMessage.text?.clear()
                        binding.etMessage.clearFocus()
                        adapter.updateNewOutgoingMessage(it.data)

                        Handler(Looper.getMainLooper()).postDelayed({
                            scrollToBottom(adapter.itemCount - 1)
                        }, 500)
                    }
                    is Resource.Loading -> {
                    }
                    is Resource.Error -> Timber.e(it.exception)
                }
            }
        })

        viewModel.recentlyIncomingMessage.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        adapter.updateNewIncomingMessage(it.data)
                        Handler(Looper.getMainLooper()).postDelayed({
                            scrollToBottom(adapter.itemCount - 1)
                        }, 500)
                    }
                    is Resource.Error -> Timber.e(it.exception)
                    Resource.Loading -> {
                    }
                }
            }
        })

    }

    private fun scrollToBottom(position: Int) {
        binding.recyclerGchat.scrollToPosition(position)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}