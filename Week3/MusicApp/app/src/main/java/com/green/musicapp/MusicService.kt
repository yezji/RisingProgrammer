package com.green.musicapp

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder

class MusicService : Service() {
    var player:MediaPlayer? = null
    private val binder:IBinder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService():MusicService = this@MusicService
    }

    override fun onCreate() {
        // Service에서 가장 먼저 호출 (최초 한번)
        // 인텐트 정보를 받은 곳부터 시작해야 하기에 나는 onStartCommand에서 초기화 한다
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Service가 시작된 뒤에 호출
        intent?.let {
            val musicRid = it.getIntExtra("music", R.raw.sound_1)
            val status = it.getBooleanExtra("status", false)
            val position = it.getIntExtra("position", 0)

            if (player == null) {
                player = MediaPlayer.create(applicationContext, musicRid)
                player?.isLooping = false // 반복재생 여부
                player?.start()
                player?.pause()
                player?.setOnCompletionListener { _ -> stopSelf() } // 스스로 종료
                player?.seekTo(position)
            }
        }

        // 시스템에 의해 종료되면 다시 실행되지 않음
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        // Context.bindService()를 통해 Service가 바인드 되는 경우에 호출
        // Service 객체와 Activity 사이에서 통신할 때 사용되는 메서드
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        // Service 종료
        player?.stop()
        player?.release()
        player = null
    }

    fun musicTimeSet(time:Int) {
        // 원하는 구간으로 이동
        player?.let {
            it.pause()
            it.seekTo(time)
            it.start()
        }
    }

    fun musicStop() {
        player?.pause()
    }

    fun musicStart() {
        player?.start()
    }

}
