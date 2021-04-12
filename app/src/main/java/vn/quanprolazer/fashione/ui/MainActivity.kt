/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */


package vn.quanprolazer.fashione.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import timber.log.Timber
import vn.quanprolazer.fashione.R
import vn.quanprolazer.fashione.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // turn off splash screen
        setTheme(R.style.Theme_Fashione)

        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        Timber.plant(Timber.DebugTree())

        setupNavigation()

        setupNavigationItemClick()
    }

    private fun setupNavigationItemClick() {
        binding.navigationView.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.menu_item_login -> {
                    Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
                }
                else -> {
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onSupportNavigateUp() =
        navigateUp(findNavController(R.id.nav_host_fragment), binding.drawerLayout)


    /**
     * Setup Navigation for this Activity
     */
    private fun setupNavigation() {
        // first find the nav controller
        val navController = findNavController(R.id.nav_host_fragment)

        setSupportActionBar(binding.toolbar)

        // then setup the action bar, tell it about the DrawerLayout
        setupActionBarWithNavController(navController, binding.drawerLayout)

        // remove toolbar title
//        supportActionBar?.setDisplayShowTitleEnabled(false);


        // finally setup the left drawer (called a NavigationView)
        binding.navigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
            val toolBar = supportActionBar ?: return@addOnDestinationChangedListener
            when (destination.id) {
                R.id.homeFragment -> {
//                    toolBar.setDisplayShowTitleEnabled(false)
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


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

