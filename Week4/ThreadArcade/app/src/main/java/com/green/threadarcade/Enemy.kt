package com.green.threadarcade


open class Enemy : Fish {
    constructor(x: Float, y: Float, weightLevel: Int, speed: Int, alive: Boolean) : super(x, y, weightLevel, speed, alive) {
        when (weightLevel) {
            0 -> {
                this.aimScore = 10
            }
            1 -> {
                this.aimScore = 30
            }
            2 -> {
                this.aimScore = 60
            }
            3 -> {
                this.aimScore = 100
            }
            4 -> {
                this.aimScore = 150
            }
            5 -> {
                this.aimScore = 210
            }
            6 -> {
                this.aimScore = 280
            }
            7 -> {
                this.aimScore = 360
            }
        }
    }
}