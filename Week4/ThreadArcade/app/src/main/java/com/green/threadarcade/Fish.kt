package com.green.threadarcade

open class Fish {
    open var x:Float
    open var y:Float
    open var direction: Boolean
    open var weightLevel:Int
    open var speed:Int
    open var aimScore: Int
    open var alive:Boolean

    constructor(x: Float, y: Float, weightLevel: Int, speed: Int, alive: Boolean) {
        this.x = x
        this.y = y
        this.weightLevel = weightLevel
        this.speed = speed
        this.alive = alive

        this.direction = x > 0f
        this.aimScore = 0
    }

    open fun move(dtX:Int, dtY:Int) {
        this.x += dtX
        this.y += dtY
    }



}