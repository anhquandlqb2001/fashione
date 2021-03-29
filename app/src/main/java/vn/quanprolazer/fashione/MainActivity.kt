package vn.quanprolazer.fashione

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import vn.quanprolazer.fashione.databinding.ActivityMainBinding
import vn.quanprolazer.fashione.domain.Product
//import vn.quanprolazer.fashione.network.FashioneProductAdminService

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // turn off splash screen
        setTheme(R.style.Theme_Fashione)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupNavigation()
    }

    override fun onSupportNavigateUp() = navigateUp(findNavController(R.id.nav_host_fragment), binding.drawerLayout)


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
            when(destination.id) {
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

}

