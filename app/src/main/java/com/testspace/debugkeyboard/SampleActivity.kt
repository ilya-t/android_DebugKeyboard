package com.testspace.debugkeyboard

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import java.lang.RuntimeException

class SampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        findViewById<View>(R.id.open_settings).setOnClickListener {
            startActivity(Intent("android.settings.INPUT_METHOD_SETTINGS"))
        }

        findViewById<View>(R.id.crash_app).setOnClickListener {
            // Used to check crash reports and obfuscation.
            throw RuntimeException("CrashTest!")
        }
        checkPermissions()
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 711)
        }

    }
}
