package com.green.threadarcade

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect

class SpriteAnimation : Canvas {
    private var srcArea: Rect
    private var spriteWidth: Int
    private var spriteHeight: Int
    private var fps: Int
    private var maxFrame: Int
    private var currentFrame: Int
    private var frameTimer: Long

    constructor(bitmap: Bitmap) : super(bitmap) {
        this.srcArea = Rect(0, 0, 0, 0)

        this.spriteWidth = 0
        this.spriteHeight = 0
        this.fps = 0
        this.maxFrame = 0
        this.currentFrame = 0

        this.currentFrame = 0
        this.frameTimer = 0
    }

    fun initSpriteData(width: Int, height: Int, fps: Int, frameCount: Int) {
        this.spriteWidth = width
        this.spriteHeight = height

        this.srcArea.left = 0
        this.srcArea.top = 0
        this.srcArea.right = this.spriteWidth
        this.srcArea.bottom = this.spriteHeight

        if (fps <= 0) this.fps = 1000 / 1
        else this.fps = 1000 / fps
        this.maxFrame = frameCount
    }

    fun drawSprite(canvas: Canvas, bitmap: Bitmap, startX: Int, startY: Int, width: Int, height: Int) {
        var destArea: Rect = Rect(startX, startY, startX+width, startY+height)
        canvas.drawBitmap(bitmap, srcArea, destArea, null)
    }

    fun updateSprite(gameTime: Long) {
        // 시간의 흐름에 따른 스프라이트 애니메이션 재생
        if (gameTime > this.frameTimer+this.fps) {
            frameTimer = gameTime
            this.currentFrame += 1
            if (this.currentFrame >= this.maxFrame) {
                this.currentFrame = 0
            }
        }

        // 다음 프레임으로 넘어가기
        this.srcArea.left = this.currentFrame * this.spriteWidth
        this.srcArea.right = this.srcArea.left + this.spriteWidth
    }


}