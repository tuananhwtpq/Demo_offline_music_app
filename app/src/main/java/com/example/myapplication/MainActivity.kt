package com.example.myapplication

import android.app.ActionBar
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.CustomToolBarBinding
import com.google.android.material.navigation.NavigationView

class MainActivity() : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toggle =
            ActionBarDrawerToggle(
                this,
                binding.drawerLayout,
                R.string.nav_open,
                R.string.nav_close
            )

        binding.drawerLayout.addDrawerListener(toggle)

        toggle.syncState()

        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setSupportActionBar(binding.myToolbarInclude.toolBarMain)

        val btnMenu = binding.myToolbarInclude.btnMenu
        val btnSearch = binding.myToolbarInclude.btnSearch

        btnMenu.setOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        setupDrawerLayout()
        handleNavigationItemSelected()
        handleBottomNavView()
//        handleNavigationClose()
    }

    private fun handleBottomNavView() {
        binding.bottomNavView.background = null
        binding.bottomNavView.menu.getItem(2).isEnabled = false
    }

    private fun setupDrawerLayout() {
        val drawerWidth = 250
        val destiny = resources.displayMetrics.density
        val slideRange = (drawerWidth * destiny).toInt()

        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                val slideX = slideRange * slideOffset
                binding.mainLayout.translationX = slideX
            }

            override fun onDrawerOpened(drawerView: View) {}

            override fun onDrawerClosed(drawerView: View) {}

            override fun onDrawerStateChanged(newState: Int) {}
        })
    }


    private fun handleNavigationClose() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    finish()
                }
            }

        })
    }


    private fun handleNavigationItemSelected() {
        binding.navigationView.setNavigationItemSelectedListener(
            object : NavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {

                    when (item.itemId) {
                        R.id.menu_profile -> {
                            Toast.makeText(this@MainActivity, "Profile menu", Toast.LENGTH_SHORT)
                                .show()
                        }

                        R.id.menu_setting -> {
                            Toast.makeText(this@MainActivity, "Setting menu", Toast.LENGTH_SHORT)
                                .show()
                        }

                        R.id.menu_my_account -> {
                            Toast.makeText(this@MainActivity, "My Account menu", Toast.LENGTH_SHORT)
                                .show()
                        }

                    }

                    binding.drawerLayout.closeDrawers()
                    return true
                }
            }
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }
}