package com.putaolw.translation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.projection.MediaProjectionManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val projection = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            startService(Intent(this, CaptureService::class.java).apply { putExtra("resultCode", result.resultCode); putExtra("data", result.data) })
            startService(Intent(this, OverlayService::class.java))
            status.text = "已授权，正在捕获手机播放声音"
        }
    }
    private lateinit var status: TextView
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(32, 48, 32, 32) }
        root.addView(TextView(this).apply { text = "浮译字幕"; textSize = 28f })
        status = TextView(this).apply { text = "请先授权"; textSize = 16f; setPadding(0, 24, 0, 24) }; root.addView(status)
        root.addView(Button(this).apply { text = "允许悬浮窗"; setOnClickListener { startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))) } })
        root.addView(Button(this).apply { text = "捕获手机播放声音"; setOnClickListener { requestAudioAndCapture() } })
        root.addView(Button(this).apply { text = "停止"; setOnClickListener { stopService(Intent(this@MainActivity, CaptureService::class.java)); stopService(Intent(this@MainActivity, OverlayService::class.java)); status.text = "已停止" } })
        setContentView(root)
    }
    private fun requestAudioAndCapture() {
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), 10)
        if (!Settings.canDrawOverlays(this)) { status.text = "请先允许悬浮窗"; return }
        val manager = getSystemService(MediaProjectionManager::class.java); projection.launch(manager.createScreenCaptureIntent())
    }
}
