package com.putaolw.translation

import android.app.*
import android.content.*
import android.media.*
import android.media.projection.MediaProjectionManager
import android.os.*
import androidx.core.app.NotificationCompat

class CaptureService : Service() {
    private var record: AudioRecord? = null; private var thread: Thread? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createChannel(); startForeground(9, NotificationCompat.Builder(this, "capture").setContentTitle("浮译字幕正在运行").setSmallIcon(android.R.drawable.ic_btn_speak_now).build())
        val code = intent?.getIntExtra("resultCode", 0) ?: return START_NOT_STICKY; val token = intent.getParcelableExtra<Intent>("data") ?: return START_NOT_STICKY
        val projection = getSystemService(MediaProjectionManager::class.java).getMediaProjection(code, token)
            ?: return START_NOT_STICKY
        val config = AudioPlaybackCaptureConfiguration.Builder(projection).addMatchingUsage(AudioAttributes.USAGE_MEDIA).build()
        val min = AudioRecord.getMinBufferSize(16000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT)
        record = AudioRecord.Builder().setAudioFormat(AudioFormat.Builder().setEncoding(AudioFormat.ENCODING_PCM_16BIT).setSampleRate(16000).setChannelMask(AudioFormat.CHANNEL_IN_MONO).build()).setBufferSizeInBytes(min * 2).setAudioPlaybackCaptureConfig(config).build()
        try { record?.startRecording() } catch (_: SecurityException) { stopSelf(); return START_NOT_STICKY }
        thread = Thread { val buffer = ByteArray(min); while (!Thread.currentThread().isInterrupted) { val n = record?.read(buffer, 0, buffer.size) ?: 0; if (n > 0) OverlayService.publishAudio(buffer, n) } }; thread?.start(); return START_STICKY
    }
    private fun createChannel() { getSystemService(NotificationManager::class.java).createNotificationChannel(NotificationChannel("capture", "浮译字幕", NotificationManager.IMPORTANCE_LOW)) }
    override fun onDestroy() { thread?.interrupt(); record?.stop(); record?.release(); super.onDestroy() }
    override fun onBind(intent: Intent?) = null
}
