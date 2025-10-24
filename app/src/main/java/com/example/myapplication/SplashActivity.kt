package com.example.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.ui.AppBarConfiguration
import com.example.myapplication.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private var isHasPermission: Boolean = false

    private val requestPermisstionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                isHasPermission = true
                navigateToMain()
            } else {
                isHasPermission = false
                navigateToMain()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener { handleAccessPermission() }

    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("has_permission", isHasPermission)
        startActivity(intent)
    }

    private fun handleAccessPermission() {

        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_AUDIO
            ) == PackageManager.PERMISSION_GRANTED -> {
                isHasPermission = true
                navigateToMain()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_MEDIA_AUDIO
            ) -> {
                showPermissionRequest()
            }

            else -> {
                requestPermisstionLauncher.launch(Manifest.permission.READ_MEDIA_AUDIO)
            }
        }
    }

    private fun showPermissionRequest() {

        AlertDialog.Builder(this)
            .setTitle("Xin cap quyen")
            .setMessage("Ung dung nay can duoc cap quyen de su dung app")
            .setPositiveButton("OK") { _, _ ->
                requestPermisstionLauncher.launch(Manifest.permission.READ_MEDIA_AUDIO)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

}