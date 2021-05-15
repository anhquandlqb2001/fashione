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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import vn.quanprolazer.fashione.data.domain.model.Resource
import vn.quanprolazer.fashione.databinding.FragmentWriteReviewBinding
import vn.quanprolazer.fashione.utilities.ViewDialog
import vn.quanprolazer.fashione.viewmodels.WriteReviewViewModel
import javax.inject.Inject

@AndroidEntryPoint
class WriteReviewFragment : Fragment() {

    private var _binding: FragmentWriteReviewBinding? = null

    private val binding: FragmentWriteReviewBinding get() = _binding!!

    @Inject
    lateinit var writeReviewViewModelFactory: WriteReviewViewModel.AssistedFactory
    private val viewModel: WriteReviewViewModel by viewModels {
        WriteReviewViewModel.provideFactory(
            writeReviewViewModelFactory,
            WriteReviewFragmentArgs.fromBundle(requireArguments()).purchaseToAddReview
        )
    }

    private val loadingDialog: ViewDialog by lazy { ViewDialog(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWriteReviewBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.addReviewStatus.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        if (viewModel.isDialogShowing) {
                            loadingDialog.hideDialog()
                            viewModel.doneAddReview()

                            this.findNavController().popBackStack()
                            makeSnackBar("Thêm nhận xét thành công")
                        }
                    }
                    is Resource.Loading -> {
                        if (!viewModel.isDialogShowing) {
                            loadingDialog.showDialog()
                            viewModel.isDialogShowing = true
                        }
                    }
                    is Resource.Error -> {
                        makeSnackBar("Thêm nhận xét thất bại")
                    }
                }
            }
        })
    }

    private fun makeSnackBar(string: String) {
        Snackbar.make(
            binding.root,
            string,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}