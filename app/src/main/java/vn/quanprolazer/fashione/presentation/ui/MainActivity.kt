/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */


package vn.quanprolazer.fashione.presentation.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.databinding.ActivityMainBinding
import vn.quanprolazer.fashione.domain.models.AuthenticationState
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.firebase.FashioneFirebaseMessagingService
import vn.quanprolazer.fashione.presentation.viewmodels.LoginViewModel
import vn.quanprolazer.fashione.presentation.viewmodels.MainViewModel


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null

    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // turn off splash screen
        setTheme(R.style.Theme_Fashione)

        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupNavigation()
        setupNavigationItemClick()
        observeAuthenticationState()

        observeNavigateToNotification()

        observeNavigateToCart()

        viewModel.notificationCount.observe(this, {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        binding.ivNotification.badgeValue = it.data
                    }
                    is Resource.Error -> Timber.e(it.exception)
                }
            }
        })

        viewModel.cartItemCount.observe(this, {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        binding.ivCart.badgeValue = it.data
                    }
                    is Resource.Error -> Timber.e(it.exception)
                }
            }
        })
    }

    private fun observeNavigateToCart() {
        viewModel.navigateToCart.observe(this, {
            it?.let {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToCartFragment()
                this.findNavController(R.id.nav_host_fragment).navigate(action)
                viewModel.doneNavigateToCart()
            }
        })
    }

    private fun observeNavigateToNotification() {
        viewModel.navigateToNotification.observe(this, {
            it?.let {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToNotificationFragment()
                this.findNavController(R.id.nav_host_fragment).navigate(action)
                viewModel.doneNavigateToNotification()
            }
        })
    }

    override fun onSupportNavigateUp() =
        navigateUp(findNavController(R.id.nav_host_fragment), binding.drawerLayout)


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun observeAuthenticationState() {
        loginViewModel.authenticationState.observe(this, { authenticationState ->
            val menuItem = binding.navigationView.menu.findItem(R.id.menu_item_login)
            when (authenticationState) {
                AuthenticationState.AUTHENTICATED -> {
                    menuItem.setOnMenuItemClickListener {
                        AuthUI.getInstance().signOut(applicationContext)
                        logoutSuccess()
                        binding.apply {
                            ivNotification.badgeValue = 0
                            ivCart.badgeValue = 0
                        }
                        closeDrawerWithAnimation()
                        true
                    }

                    // save FCM token to firestore
                    FashioneFirebaseMessagingService().getToken(applicationContext)?.let {
                        loginViewModel.updateFCMToken(
                            it
                        )
                    }

                    viewModel.apply {
                        fetchCartItemCount()
                        fetchNotificationCount()
                    }

                    binding.navigationView.menu.findItem(R.id.menu_item_personal).isVisible = true
                    binding.navigationView.menu.findItem(R.id.menu_item_message).isVisible = true
                    menuItem.title = getString(R.string.sign_out_text)
                }
                else -> {
                    menuItem.setOnMenuItemClickListener {
                        launchSignInFlow()
                        closeDrawerWithAnimation()
                        true
                    }
                    binding.navigationView.menu.findItem(R.id.menu_item_personal).isVisible = false
                    binding.navigationView.menu.findItem(R.id.menu_item_message).isVisible = false
                    menuItem.title = getString(R.string.login)
                }
            }
        })
    }


    /**
     * Setup Navigation Drawer item click
     */
    private fun setupNavigationItemClick() {
        binding.navigationView.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.menu_item_home -> {
                    navigateToFragment(R.id.homeFragment)
                }
                R.id.menu_item_login -> {
                    launchSignInFlow()
                }
                R.id.menu_item_cart -> {
                    navigateToFragment(R.id.cartFragment)
                }
                R.id.menu_item_personal -> {
                    navigateToFragment(R.id.personalFragment)
                }
                R.id.menu_item_videos -> {
                    navigateToFragment(R.id.videosFragment)
                }
                R.id.menu_item_message -> navigateToFragment(R.id.messageFragment)
                else -> {
                }
            }
            closeDrawerWithAnimation()
            true
        }
    }


    private fun navigateToFragment(resId: Int) =
        this.findNavController(R.id.nav_host_fragment).navigate(resId)


    /**
     * Close navigation drawer to START (left side)
     */
    private fun closeDrawerWithAnimation() = binding.drawerLayout.closeDrawer(GravityCompat.START)


    /**
     * Setup Navigation for this Activity
     */
    private fun setupNavigation() {
        // first find the nav controller
        val navController = findNavController(R.id.nav_host_fragment)

        setSupportActionBar(binding.toolbar)
        // then setup the action bar, tell it about the DrawerLayout
        setupActionBarWithNavController(navController, binding.drawerLayout)

        // finally setup the left drawer (called a NavigationView)
        binding.navigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
            val toolBar = supportActionBar ?: return@addOnDestinationChangedListener
            toolBar.setDisplayShowTitleEnabled(false)
            when (destination.id) {
                R.id.homeFragment -> {
                    // custom menu icon
                    customActionBarTitleVisibility(View.GONE)
                    toolBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_36dp)
                    customActionBarTitleVisibility(View.GONE)
                    binding.ivCart.visibility = View.VISIBLE
                    binding.ivNotification.visibility = View.VISIBLE
                }
                R.id.pickupAddressFragment -> {
                    setupFragmentWithoutIcon(toolBar, getString(R.string.text_pickup_address))
                }
                R.id.addPickupAddressFragment -> {
                    setupFragmentWithoutIcon(toolBar, getString(R.string.text_add_new_pickup_address))
                }
                R.id.personalFragment -> {
                    setupFragmentWithoutIcon(toolBar, getString(R.string.menu_item_personal_sub_title))
                }
                R.id.purchaseMenuFragment -> {
                    setupFragmentWithoutIcon(toolBar, getString(R.string.text_purchase))
                }
                R.id.writeReviewFragment -> {
                    setupFragmentWithoutIcon(toolBar, getString(R.string.text_add_review))
                }
                R.id.reviewFragment -> {
                    setupFragmentWithoutIcon(toolBar, getString(R.string.text_add_review))
                }
                R.id.notificationFragment -> {
                    setupFragmentWithoutIcon(toolBar, getString(R.string.text_notification))
                }
                R.id.videosFragment -> {
                    setupFragmentWithoutIcon(toolBar, getString(R.string.text_live_video))
                }
                R.id.videoPlayerFragment -> setupFragmentWithoutIcon(toolBar, getString(R.string.text_video_playing))
                R.id.cartFragment -> setupFragmentWithoutIcon(toolBar, getString(R.string.menu_item_cart_text))
                else -> {
                    customActionBarTitleVisibility(View.GONE)
                    binding.ivCart.visibility = View.GONE
                    binding.ivNotification.visibility = View.GONE
                    toolBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_black_36dp)
                }
            }
        }
    }

    private fun setupFragmentWithoutIcon(toolBar: ActionBar, text: String) {
        customActionBarTitleVisibility(View.VISIBLE)
        toolBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_black_36dp)
        binding.tvToolbarTitle.text = text
        binding.ivCart.visibility = View.GONE
        binding.ivNotification.visibility = View.GONE
    }

    private fun customActionBarTitleVisibility(visibility: Int) {
        binding.tvToolbarTitle.visibility = visibility
    }


    /**
     * Function to launch login intent (provide by firebase auth UI)
     */
    private fun launchSignInFlow() {
        // Give users the option to sign in / register with their email or Google account.
        // If users choose to register with their email,
        // they will need to create a password as well.
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build()
        )

        resultLauncher.launch(
            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers)
                .setTheme(R.style.LoginTheme).build()
        )
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data
            val response = IdpResponse.fromResultIntent(data)

            if (result.resultCode == Activity.RESULT_OK) {
                // Successfully signed in

                successLogin()
            } else {
                if (response != null) {
                    when (response.error?.errorCode) {
                        ErrorCodes.NO_NETWORK -> errorNetworkLogin()
                        else -> errorUnHandledLogin()
                    }
                }

            }
        }

    /**
     * Function to control UI when success login
     */
    private fun successLogin() =
        Snackbar.make(binding.root, "????ng nh???p th??nh c??ng", Snackbar.LENGTH_SHORT).show()


    /**
     * Function to control UI when error login cause NO_NETWORK
     */
    private fun errorNetworkLogin() = Toast.makeText(this, "L???i m???ng", Toast.LENGTH_LONG).show()


    /**
     * Function to control UI when an unhandled error throw
     */
    private fun errorUnHandledLogin() =
        Toast.makeText(this, "C?? l???i x???y ra", Toast.LENGTH_LONG).show()


    private fun logoutSuccess() = Toast.makeText(this, "???? ????ng xu???t", Toast.LENGTH_SHORT).show()
}

