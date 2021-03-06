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
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import vn.quanprolazer.fashione.databinding.FragmentWriteReviewBinding
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.presentation.utilities.LoadingDialog
import vn.quanprolazer.fashione.presentation.viewmodels.WriteReviewViewModel
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

    private val loadingDialog: LoadingDialog by lazy { LoadingDialog(requireActivity()) }

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
                            makeSnackBar("Th??m nh???n x??t th??nh c??ng")
                        }
                    }
                    is Resource.Loading -> {
                        if (!viewModel.isDialogShowing) {
                            loadingDialog.showDialog()
                            viewModel.isDialogShowing = true
                        }
                    }
                    is Resource.Error -> {
                        makeSnackBar("Th??m nh???n x??t th???t b???i")
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