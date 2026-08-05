package com.putaolw.translation

import android.app.*
import android.content.*
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.*
import android.widget.TextView
import android.os.Handler
import android.os.Looper
import java.util.concurrent.atomic.AtomicReference

class OverlayService : Service() {
    private var window: View? = null; private val text = AtomicReference("等待英文字幕…")
    override fun onCreate() { super.onCreate(); instance = this; val v = TextView(this).apply { setTextColor(Color.WHITE); setBackgroundColor(0xCC111111.toInt()); textSize = 16f; setPadding(24, 12, 24, 12); text = "浮译字幕已开启\n等待视频声音…"; setOnClickListener { stopSelf() } }; window = v; try { getSystemService(WindowManager::class.java).addView(v, WindowManager.LayoutParams(-1, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT)) } catch (_: Exception) { stopSelf() } }
    override fun onDestroy() { instance = null; window?.let { try { getSystemService(WindowManager::class.java).removeView(it) } catch (_: Exception) {} }; super.onDestroy() }
    override fun onBind(intent: Intent?): IBinder? = null
    companion object { private var instance: OverlayService? = null; private val main = Handler(Looper.getMainLooper()); fun publishAudio(bytes: ByteArray, length: Int) { /* PCM enters the speech engine integration point. */ } }
}
