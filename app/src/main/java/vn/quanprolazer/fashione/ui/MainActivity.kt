/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */


package vn.quanprolazer.fashione.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
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
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.databinding.ActivityMainBinding
import vn.quanprolazer.fashione.data.domain.model.AuthenticationState
import vn.quanprolazer.fashione.viewmodels.LoginViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null

    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // turn off splash screen
        setTheme(R.style.Theme_Fashione)

        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupNavigation()
        setupNavigationItemClick()
        observeAuthenticationState()
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
                        closeDrawerWithAnimation()
                        logoutSuccess()
                        true
                    }
                    menuItem.title = getString(R.string.sign_out_text)
                }
                else -> {
                    menuItem.setOnMenuItemClickListener {
                        launchSignInFlow()
                        closeDrawerWithAnimation()
                        true
                    }
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
            when (destination.id) {
                R.id.homeFragment -> {
                    // custom menu icon
                    toolBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_36dp)
                }
                else -> {
                    toolBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_black_36dp)
                    toolBar.setDisplayHomeAsUpEnabled(true)
                }
            }
        }
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

    var resultLauncher =
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
        Snackbar.make(binding.root, "Đăng nhập thành công", Snackbar.LENGTH_SHORT).show()


    /**
     * Function to control UI when error login cause NO_NETWORK
     */
    private fun errorNetworkLogin() = Toast.makeText(this, "Lỗi mạng", Toast.LENGTH_LONG).show()


    /**
     * Function to control UI when an unhandled error throw
     */
    private fun errorUnHandledLogin() =
        Toast.makeText(this, "Có lỗi xảy ra", Toast.LENGTH_LONG).show()


    private fun logoutSuccess() = Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show()
}

