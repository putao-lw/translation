package com.putaolw.translation

import android.app.*
import android.content.*
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.*
import android.widget.TextView
import java.util.concurrent.atomic.AtomicReference

class OverlayService : Service() {
    private var window: View? = null; private val text = AtomicReference("等待英文字幕…")
    override fun onCreate() { super.onCreate(); val v = TextView(this).apply { setTextColor(Color.WHITE); setBackgroundColor(0xCC111111.toInt()); textSize = 16f; setPadding(24, 12, 24, 12); setOnClickListener { stopSelf() } }; window = v; getSystemService(WindowManager::class.java).addView(v, WindowManager.LayoutParams(-1, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT)) }
    override fun onDestroy() { window?.let { getSystemService(WindowManager::class.java).removeView(it) }; super.onDestroy() }
    override fun onBind(intent: Intent?): IBinder? = null
    companion object { private var instance: OverlayService? = null; fun publishAudio(bytes: ByteArray, length: Int) { /* AudioRecord PCM is handed to the speech engine in the next milestone. */ } }
}
