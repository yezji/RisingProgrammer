package com.green.threadarcade

import android.graphics.Bitmap

class Player : Fish {
    var bitmaps: MutableList<MutableList<Bitmap>>
    var totalScore: Int

    constructor(x: Float, y: Float, weightLevel: Int, speed: Int, alive: Boolean) : super(x, y, weightLevel, speed, alive) {
        this.bitmaps = mutableListOf()
        this.totalScore = 0

        getAimScore(weightLevel)
    }

    fun getAimScore(nowWeight: Int) : Int {
        var score = 0
        when (weightLevel) {
            0 -> {
                score = 50
            }
            1 -> {
                score = 100 + (50)
            }
            2 -> {
                score = 300 + (150+50)
            }
            3 -> {
                score = 500 + (300+150+50)
            }
            4 -> {
                score = 750 + (500+300+150+50)
            }
            5 -> {
                score = 1050 + (750+500+300+150+50)
            }
            6 -> {
                score = 1120 + (1050+750+500+300+150+50)
            }
            7 -> {
                score = 2000 + (1120+1050+750+500+300+150+50)
            }
        }
        return score
    }

    fun getImgSize(nowWeight: Int) : Int {
        var sideSize = 0
        when (nowWeight) {
            0 -> {
                sideSize = 60
            }
            1 -> {
                sideSize = 80
            }
            2 -> {
                sideSize = 100
            }
            3 -> {
                sideSize = 120
            }
            4 -> {
                sideSize = 140
            }
            5 -> {
                sideSize = 180
            }
            6 -> {
                sideSize = 220
            }
            7 -> {
                sideSize = 340
            }
            8 -> {
                sideSize = 60
            }
        }
        return sideSize
    }
}