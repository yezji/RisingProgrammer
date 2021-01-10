package com.green.musicapp

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class PlayMusicService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    /*override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var message:Boolean = intent.getExtras().getBoolean(PlayMusicService.MESSEAGE_KEY)
        if (message) {
            mediaPlayer = MediaPlayer.create(this, R.raw.sound_1)
            mediaPlayer.start()
        }
        else {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
        
        // 시스템에 의해 종료되면 다시 실행되지 않도록
        return START_NOT_STICKY
    }*/
}