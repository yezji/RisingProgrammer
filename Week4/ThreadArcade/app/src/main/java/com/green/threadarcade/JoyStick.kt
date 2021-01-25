package com.green.threadarcade

import android.graphics.Color
import android.graphics.Paint

class JoyStick {
    var screenWidth: Int
    var screenHeight: Int
    var rightJoyStick: Boolean

    var backPosX: Float = 0f
    var backPosY: Float = 0f
    var frontPosX: Float = 0f
    var frontPosY: Float = 0f

    var backPaint: Paint = Paint()
    var frontPaint: Paint = Paint()

    constructor(rightJoyStick: Boolean, screenWidth: Int, screenHeight: Int) {
        this.screenWidth = screenWidth
        this.screenHeight = screenHeight

        this.rightJoyStick = rightJoyStick
        changeHand(rightJoyStick)

        backPaint.setColor(Color.argb(22, 51, 51, 51))
        frontPaint.setColor(Color.argb(185, 255, 255, 255))
        frontPaint.setShadowLayer(12f, 0f, 0f, Color.argb(33, 0, 0, 0))
    }

    fun changeHand(isRightHand: Boolean) {
        this.rightJoyStick = isRightHand
        if (rightJoyStick) {
            this.backPosX = screenWidth-300f
            this.backPosY = screenHeight-300f
            this.frontPosX = backPosX
            this.frontPosY = backPosY
        }
        else {
            this.backPosX = 300f
            this.backPosY = screenHeight-300f
            this.frontPosX = backPosX
            this.frontPosY = backPosY
        }
    }

}