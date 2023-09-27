package com.example.first_mgr

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.first_mgr.databinding.ActivityMenuBinding
import com.google.android.material.navigation.NavigationView

class menu_activity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMenu.toolbar)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.teal_500)))


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navHome
        val navController = findNavController(R.id.nav_host_fragment_content_menu)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_newsy
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.activity_main_drawer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_trening_menu -> {
                // Start the ExercisesActivity
                val intent = Intent(this, trening_menu::class.java)
                startActivity(intent)
                true
            }

            R.id.nav_newsy -> {
                // Start the TwitterActivity (or replace it with your Twitter view activity)
                val intent = Intent(this, newsy_activity::class.java)
                startActivity(intent)
                true
            }

            R.id.nav_notatnik -> {
                // Start the TwitterActivity (or replace it with your Twitter view activity)
                val intent = Intent(this, NotatnikActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.nav_gry -> {
                // Start the GryActivityScreen activity
                val intent = Intent(this, GryActivityScreen::class.java)
                startActivity(intent)
                true
            }
            R.id.nav_drawing -> {
                val intent = Intent(this, DrawingFragment::class.java)
                startActivity(intent)
                true
            }
            else -> {
                // Handle any other menu items here
                super.onOptionsItemSelected(item)
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_menu)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}
