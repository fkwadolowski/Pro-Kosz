package com.example.first_mgr

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_newsy, R.id.nav_notatnik, R.id.nav_gry, R.id.nav_drawing
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_trening_menu -> {
                val intent = Intent(this, trening_menu::class.java)
                startActivity(intent)
                true
            }
            R.id.nav_newsy -> {
                val intent = Intent(this, newsy_activity::class.java)
                startActivity(intent)
                true
            }
            R.id.nav_notatnik -> {
                val intent = Intent(this, NotatnikActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.nav_gry -> {
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
                super.onOptionsItemSelected(item)
            } } }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_menu)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
