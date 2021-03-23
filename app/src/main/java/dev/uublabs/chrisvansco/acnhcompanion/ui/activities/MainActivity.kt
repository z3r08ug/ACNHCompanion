package dev.uublabs.chrisvansco.acnhcompanion.ui.activities

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.uublabs.chrisvansco.acnhcompanion.R
import dev.uublabs.chrisvansco.acnhcompanion.model.SeaCreature
import dev.uublabs.chrisvansco.acnhcompanion.ui.base.BaseActivity
import dev.uublabs.chrisvansco.acnhcompanion.util.BugViewModel
import dev.uublabs.chrisvansco.acnhcompanion.util.FishViewModel
import dev.uublabs.chrisvansco.acnhcompanion.util.SeaCreatureDictionary
import dev.uublabs.chrisvansco.acnhcompanion.util.SeaCreatureViewModel

class MainActivity : BaseActivity() {
//    private val sampleTV: TextView by lazy { findViewById(R.id.sampleTV) as TextView }
    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar);
        supportActionBar?.title = "ACNH Companion"

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return
        navController = host.navController
        setupBottomNavMenu(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                destination.id.toString()
            }
            Log.d("NavigationActivity", "Navigated to $dest")
        }

        fishViewModel = ViewModelProvider(this).get(FishViewModel::class.java)
        bugViewModel = ViewModelProvider(this).get(BugViewModel::class.java)
        seaCreatureViewModel = ViewModelProvider(this).get(SeaCreatureViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_overflow, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
            R.id.action_logout -> {
                auth.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNV)
        bottomNav?.setupWithNavController(navController)
    }

    companion object {
        lateinit var fishViewModel: FishViewModel
        lateinit var bugViewModel: BugViewModel
        lateinit var seaCreatureViewModel: SeaCreatureViewModel
        private val TAG = MainActivity::class.simpleName
    }
}