package com.green.threadarcade

import android.content.Context
import android.content.SharedPreferences
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import java.lang.Exception

class GameSurfaceView : SurfaceView, SurfaceHolder.Callback {
    private val TAG:String = javaClass.simpleName

    companion object {
        lateinit var preferences: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
    }

    var surfaceHolder: SurfaceHolder
    var gameThread: GameThread

    var screenWidth: Int = 0
    var screenHeight: Int = 0

    var highScore = 0
    var handJoyStick = true
    lateinit var player: Player
    lateinit var enemies: Enemies

    var joyStickActive = false


    constructor(context: Context?) : super(context) {
        // 화면 크기
        val metrics:DisplayMetrics = context!!.resources.displayMetrics
        this.screenWidth = metrics.widthPixels
        this.screenHeight = metrics.heightPixels

        // SharedPreferences 데이터 저장, 제거
        preferences = context.getSharedPreferences("prefs", 0)
        editor = preferences.edit()

        // SurfaceView에 그림을 그리기 위한 Holder 생성
        surfaceHolder = holder

        // Holder에 callback 추가하여 그림그려주기 위한 변경사항이 있을 때마다 메서드 호출
        holder.addCallback(this)

        // 게임 Thread
        gameThread = GameThread(holder, context, screenWidth, screenHeight)
    }


    override fun surfaceCreated(holder: SurfaceHolder?) {
        checkSharedPreference()
        gameThread.start()
        gameThread.highScore = this.highScore
        gameThread.rightJoyStick = this.handJoyStick
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {

        checkSharedPreference()
        editor.putInt("highScore", this.highScore)
//        editor.apply() // 저장 시점 보장하지 않지만 저장
        editor.putBoolean("handJoystick", this.handJoyStick)
        editor.commit() // 반드시 저장 후 처리
    }

    fun checkSharedPreference() {
        var prefHighScore = preferences.getInt("highScore", this.gameThread.highScore)
        var prefHand = preferences.getBoolean("handJoystick", this.gameThread.rightJoyStick)

        if (this.gameThread.highScore <= prefHighScore) {
            this.gameThread.highScore = prefHighScore
            this.highScore = prefHighScore
        }
        this.gameThread.rightJoyStick = prefHand
        this.handJoyStick = prefHand
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (this.gameThread.status) {
            0 -> {
                if (event!!.action == MotionEvent.ACTION_DOWN) {


                    this.gameThread.player.x = screenWidth/2f
                    this.gameThread.player.y = screenHeight/2f
                    this.gameThread.player.weightLevel = 0
                    this.gameThread.player.speed = 50
                    this.gameThread.player.alive = true

                    this.gameThread.status = 0
                    for (idx in 0..this.gameThread.enemies.enemyList.size-1) {
                        this.gameThread.enemies.get(idx).alive = false
                        this.gameThread.level = 0
                        this.gameThread.player.totalScore = 0
                    }

                    this.gameThread.status = 1

                }
            }
            1 -> {
                this.gameThread.highScore = this.highScore
                if (event!!.action == MotionEvent.ACTION_DOWN) {
                    Log.d(TAG, "ACTION_DOWN just x y : ${event.getX()}, ${event.getY()}")
                    Log.d(TAG, "ACTION_DOWN raw x y : ${event.getRawX()}, ${event.getRawY()}")

                    // 조이스틱 범위
                    if (event.getX() >= this.gameThread.joyStick.backPosX-200f &&
                        event.getX() <= this.gameThread.joyStick.backPosX+200f &&
                        event.getY() >= this.gameThread.joyStick.backPosY-200f &&
                        event.getY() <= this.gameThread.joyStick.backPosY+200f) {
                        joyStickActive = true
                    }


                    // 일시정지 버튼 범위
                    if (event!!.getX() >= this.gameThread.screenWidth-150f &&
                        event!!.getX() <= this.gameThread.screenWidth &&
                        event!!.getY() >= 0f &&
                        event!!.getY() <= 150f) {

                        this.gameThread.status = 2
                        this.gameThread.paused = true
                    }
                }
                else if (joyStickActive && event!!.action == MotionEvent.ACTION_MOVE) {
                    // 조이스틱 범위
                    if (event.getX() >= this.gameThread.joyStick.backPosX-500f &&
                        event.getX() <= this.gameThread.joyStick.backPosX+500f &&
                        event.getY() >= this.gameThread.joyStick.backPosY-500f &&
                        event.getY() <= this.gameThread.joyStick.backPosY+500f) {
                        joyStickActive = true

                        this.gameThread.joyStick.frontPosX = event!!.getRawX()-75f
                        this.gameThread.joyStick.frontPosY = event!!.getRawY()

                        // Move Player
                        var disX = (event!!.getRawX() - this.gameThread.joyStick.backPosX) / this.gameThread.player.speed
                        var disY = (event!!.getRawY() - this.gameThread.joyStick.backPosY) / this.gameThread.player.speed
                        if (disX != 0f || disY != 0f ) {
                            if (this.gameThread.player.x >= 0f && this.gameThread.player.x <= screenWidth-this.gameThread.player.getImgSize(this.gameThread.player.weightLevel)) {
                                this.gameThread.player.x += disX
                            }
                            else if (this.gameThread.player.x < 0f || this.gameThread.player.x > screenWidth-this.gameThread.player.getImgSize(this.gameThread.player.weightLevel)) {
                                if (this.gameThread.player.x < 0f) {
                                    this.gameThread.player.x = 0f
                                }
                                else {
                                    this.gameThread.player.x = screenWidth-this.gameThread.player.getImgSize(this.gameThread.player.weightLevel).toFloat()
                                }
                            }
                            if (this.gameThread.player.y >= 0f && this.gameThread.player.y <= screenHeight-this.gameThread.player.getImgSize(this.gameThread.player.weightLevel)) {
                                this.gameThread.player.y += disY
                            }
                            else if (this.gameThread.player.y < 0f || this.gameThread.player.y > screenHeight-this.gameThread.player.getImgSize(this.gameThread.player.weightLevel)) {
                                if (this.gameThread.player.y < 0f) {
                                    this.gameThread.player.y = 0f
                                }
                                else {
                                    this.gameThread.player.y = screenHeight-this.gameThread.player.getImgSize(this.gameThread.player.weightLevel).toFloat()
                                }
                            }
                        }
                        // Set direction
                        if (disX <= 0f) this.gameThread.player.direction = true
                        else this.gameThread.player.direction = false

                        // Set boundary for JoyStick
                        if (this.gameThread.joyStick.frontPosX < this.gameThread.joyStick.backPosX-75f) {
                            this.gameThread.joyStick.frontPosX = this.gameThread.joyStick.backPosX-75f
                        }
                        if (this.gameThread.joyStick.frontPosX > this.gameThread.joyStick.backPosX+75f) {
                            this.gameThread.joyStick.frontPosX = this.gameThread.joyStick.backPosX+75f
                        }
                        if (this.gameThread.joyStick.frontPosY < this.gameThread.joyStick.backPosY-75f) {
                            this.gameThread.joyStick.frontPosY = this.gameThread.joyStick.backPosY-75f
                        }
                        if (this.gameThread.joyStick.frontPosY > this.gameThread.joyStick.backPosY+75f) {
                            this.gameThread.joyStick.frontPosY = this.gameThread.joyStick.backPosY+75f
                        }
                    }


                }
                else if (joyStickActive && event!!.action == MotionEvent.ACTION_UP) {
                    Log.d(TAG, "ACTION_UP")

                    // 놓았을 때 조이스틱 원위치
                    this.gameThread.joyStick.frontPosX = this.gameThread.joyStick.backPosX
                    this.gameThread.joyStick.frontPosY = this.gameThread.joyStick.backPosY

                    joyStickActive = false
                }
            }

            2 -> {
                if (
                    event!!.getX() >= this.gameThread.screenWidth/2f-350f &&
                    event!!.getX() <= this.gameThread.screenWidth/2-150f &&
                    event!!.getY() >= this.gameThread.screenHeight/2f &&
                    event!!.getY() <= this.gameThread.screenHeight/2+200f) {

                    if (!this.gameThread.player.alive) {
                        this.gameThread.player.x = screenWidth/2f
                        this.gameThread.player.y = screenHeight/2f
                        this.gameThread.player.weightLevel = 0
                        this.gameThread.player.speed = 50
                        this.gameThread.player.alive = true

                        this.gameThread.status = 0
                        for (idx in 0..this.gameThread.enemies.enemyList.size-1) {
                            this.gameThread.enemies.get(idx).alive = false
                            this.gameThread.level = 0
                            this.gameThread.player.totalScore = 0
                        }
                    }

                    this.gameThread.status = 1
                }
                if (
                    event!!.getX() >= this.gameThread.screenWidth/2+100f &&
                    event!!.getX() <= this.gameThread.screenWidth/2+300f &&
                    event!!.getY() >= this.gameThread.screenHeight/2f &&
                    event!!.getY() <= this.gameThread.screenHeight/2+200f) {
                    highScore = this.gameThread.highScore

                    this.highScore = this.gameThread.highScore
                    editor.putInt("highScore", this.highScore)
                    this.handJoyStick = this.gameThread.rightJoyStick
                    editor.putBoolean("handJoystick", this.handJoyStick)
                    editor.commit() // 반드시 저장 후 처리

                    this.gameThread.status = 0

                    this.gameThread.player.x = screenWidth/2f
                    this.gameThread.player.y = screenHeight/2f
                    this.gameThread.player.weightLevel = 0
                    this.gameThread.player.speed = 50
                    this.gameThread.player.alive = true
                }
            }
        }

        return true
    }

}
