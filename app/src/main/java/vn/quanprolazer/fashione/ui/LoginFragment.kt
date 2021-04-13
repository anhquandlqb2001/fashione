///*
// * Author: quanprolazer
// * Project: Fashione
// * An android shopping app writing in Kotlin
// */
//
//package vn.quanprolazer.fashione.ui
//
//import android.app.Activity
//import android.content.Intent
//import androidx.lifecycle.ViewModelProvider
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.firebase.ui.auth.AuthUI
//import com.firebase.ui.auth.IdpResponse
//import com.google.firebase.auth.FirebaseAuth
//import timber.log.Timber
//import vn.quanprolazer.fashione.databinding.FragmentLoginBinding
//import vn.quanprolazer.fashione.viewmodels.LoginViewModel
//import vn.quanprolazer.fashione.R
//
//private const val SIGN_IN_REQUEST_CODE = 1
//
//
//class LoginFragment : Fragment() {
//
//    private val viewModel: LoginViewModel by lazy {
//        ViewModelProvider(this).get(LoginViewModel::class.java)
//    }
//
//    private var _binding: FragmentLoginBinding? = null
//
//    /** This property is only valid between onCreateView and onDestroyView. */
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentLoginBinding.inflate(inflater, container, false)
//
//        binding.lifecycleOwner = viewLifecycleOwner
//
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.btnLogin.setOnClickListener { launchSignInFlow() }
//
//        viewModel.authenticationState.observe(viewLifecycleOwner, { authenticationState ->
//            when (authenticationState) {
//                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
//                    Timber.i(FirebaseAuth.getInstance().currentUser.uid)
//                }
//                else -> {
//                }
//            }
//        })
//
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }
//
//
//
//
//
//}