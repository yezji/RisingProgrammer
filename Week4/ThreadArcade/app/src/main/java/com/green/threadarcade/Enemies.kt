package com.green.threadarcade

import android.graphics.Bitmap
import java.util.*


class Enemies {
    var enemyList: MutableList<Enemy>
    var bitmaps: MutableList<MutableList<Bitmap>>
    var numMaxFish: Int
//        set(value) {this.numMaxFish = value}
    var prob = Arrays.asList(22, 20, 18, 14, 11, 9, 6)

    constructor() {
        this.enemyList = mutableListOf()
        this.bitmaps = mutableListOf()
        this.numMaxFish = 20
    }

    fun rotateWeight(nowWeight: Int) {
        when (nowWeight) {
            0 -> {
                this.prob = Arrays.asList(30, 25, 20, 10, 5, 3, 2)
            }
            1 -> {
                this.prob = Arrays.asList(20, 20, 25, 20, 5, 3, 2)
            }
            2 -> {
                this.prob = Arrays.asList(15, 20, 20, 25, 10, 5, 5)
            }
            3 -> {
                this.prob = Arrays.asList(10, 10, 20, 20, 25, 10, 5)
            }
            4 -> {
                this.prob = Arrays.asList(5, 10, 10, 15, 20, 25, 15)
            }
            5 -> {
                this.prob = Arrays.asList(5, 10, 10, 15, 20, 20, 20)
            }
            6 -> {
                this.prob = Arrays.asList(5, 10, 10, 10, 20, 15, 30)
                numMaxFish = 10
            }
            7 -> {
                this.prob = Arrays.asList(5, 10, 10, 10, 20, 15, 30)
            }
        }
    }


    fun addFish(fish: Enemy) {
        this.enemyList.add(fish)
    }

    operator fun get(idx: Int): Enemy {
        return enemyList.get(idx)
    }

    fun getSpeed(nowWeight: Int) : Int {
        var speed = 0
        when (nowWeight) {
            0 -> {
                speed = 2
            }
            1 -> {
                speed = 3
            }
            2 -> {
                speed = 4
            }
            3 -> {
                speed = 5
            }
            4 -> {
                speed = 6
            }
            5 -> {
                speed = 7
            }
            6 -> {
                speed = 8
            }
            7 -> {
                speed = 9
            }
        }
        return speed
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
                sideSize = 600
            }
        }
        return sideSize
    }

    fun getAimScore(nowWeight: Int) : Int {
        var score = 0
        when (nowWeight) {
            0 -> {
                score = 10
            }
            1 -> {
                score = 20
            }
            2 -> {
                score = 40
            }
            3 -> {
                score = 80
            }
            4 -> {
                score = 160
            }
            5 -> {
                score = 220
            }
            6 -> {
                score = 320
            }
            7 -> {
                score = 450
            }
        }
        return score
    }

}