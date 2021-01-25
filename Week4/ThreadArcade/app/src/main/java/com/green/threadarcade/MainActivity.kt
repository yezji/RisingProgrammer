package com.green.threadarcade

import android.content.pm.ActivityInfo
import android.content.res.AssetManager
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    lateinit var gameSurfaceView: GameSurfaceView
    var highScore: Int = 0
    lateinit var player: Player
    lateinit var enemies: Enemies
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 가로모드
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        // 전체 화면 만들기
        // 화면 타이틀 제거
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        // 프로그램 타이틀, 안테나 표시줄 제거
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN)
        
        // SurfaceView 올림
        gameSurfaceView = GameSurfaceView(applicationContext)
        setContentView(gameSurfaceView)
    }

    override fun onResume() {
        super.onResume()
        try{
            gameSurfaceView.gameThread.highScore = this.highScore
            gameSurfaceView.gameThread.player = this.player
            gameSurfaceView.gameThread.enemies = this.enemies
        }
        catch (exception: Exception) {
            // nothing
        }
    }

    override fun onStop() {
        super.onStop()
        try{
            this.highScore = gameSurfaceView.gameThread.highScore
            this.player = gameSurfaceView.gameThread.player
            this.enemies = gameSurfaceView.gameThread.enemies
        }
        catch (exception: Exception) {
            // nothing
        }
    }

    // 돌아가기 버튼을 눌렀을 때 앱을 종료
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.exit(0)
        }

        // onKeyDown()까지만 호출하고 onKeyPress()는 호출되지 않도록 false 처리
        return false
    }

}