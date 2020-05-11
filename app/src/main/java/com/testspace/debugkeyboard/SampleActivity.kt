package com.testspace.debugkeyboard

import android.content.Intent
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
    }
}
