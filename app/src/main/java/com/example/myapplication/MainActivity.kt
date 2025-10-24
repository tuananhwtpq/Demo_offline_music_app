package com.example.myapplication

import android.Manifest
import android.app.ActionBar
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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
    private var countNumber = 0

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                binding.coordinatorLayout.visibility = View.VISIBLE
                binding.emptyStateLayout.emptyStateLayoutFirst.visibility = View.GONE
            } else {
                Toast.makeText(this, "You need this permission", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkHasPermission()
        setupItemToolBar()
        setupDrawerLayout()
        handleNavigationItemSelected()
        handleBottomNavView()
        hanleGoToSettingButton()
    }

    private fun hanleGoToSettingButton() {
        binding.emptyStateLayout.btnGoToSetting.setOnClickListener {
            if (countNumber == 0) {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_AUDIO)
                countNumber++
            } else {
                goToSetting()
            }
        }
    }

    private fun goToSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", "com.example.myapplication", null)

        intent.data = uri
        startActivity(intent)

    }

    private fun checkHasPermission() {
        val intent = getIntent()
        val isHasPermission = intent.getBooleanExtra("has_permission", false)

        if (isHasPermission) {
            binding.coordinatorLayout.visibility = View.VISIBLE
            binding.emptyStateLayout.emptyStateLayoutFirst.visibility = View.GONE

        } else {
            binding.coordinatorLayout.visibility = View.GONE
            binding.emptyStateLayout.emptyStateLayoutFirst.visibility = View.VISIBLE
        }
    }

    private fun setupItemToolBar() {
        val btnMenu = binding.myToolbarInclude.btnMenu
        val btnSearch = binding.myToolbarInclude.btnSearch

        btnMenu.setOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        btnSearch.setOnClickListener {
            Toast.makeText(this, "Search icon", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleBottomNavView() {
        binding.bottomNavView.background = null
        binding.bottomNavView.menu.getItem(2).isEnabled = false
    }

    private fun setupDrawerLayout() {
        toggle =
            ActionBarDrawerToggle(
                this,
                binding.drawerLayout,
                R.string.nav_open,
                R.string.nav_close
            )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        setSupportActionBar(binding.myToolbarInclude.toolBarMain)

        val drawerWidth = 350
        val drawerHeight = 50
        val destiny = resources.displayMetrics.density
        val slideRangeX = (drawerWidth * destiny).toInt()
        val slideRangeY = (drawerHeight * destiny).toInt()

        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                val slideX = slideRangeX * slideOffset
                val slideY = slideRangeY * slideOffset
                binding.mainLayout.translationX = slideX
                binding.mainLayout.translationY = slideY
            }

            override fun onDrawerOpened(drawerView: View) {}

            override fun onDrawerClosed(drawerView: View) {}

            override fun onDrawerStateChanged(newState: Int) {}
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