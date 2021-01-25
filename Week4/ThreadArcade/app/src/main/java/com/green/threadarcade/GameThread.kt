package com.green.threadarcade

import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import android.graphics.*
import android.util.Log
import android.view.SurfaceHolder
import java.util.*

class GameThread : Thread {
    private val TAG:String = javaClass.simpleName

    private var holder: SurfaceHolder
    private var context: Context

    companion object {
        lateinit var preferences: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
    }
    var screenWidth:Int
    var screenHeight:Int

    var status: Int = 0
    var level: Int = 0
    var highScore: Int = 0
//    var life: Int = 3
    var paused: Boolean = false

    // fishes
    var player: Player
    var enemies: Enemies

    // image reflect
    var matrixRelfect: Matrix

    // joystick
    var rightJoyStick: Boolean = true
    var joyStick: JoyStick


    var bitmapBackground: Bitmap
    var bitmapItems: MutableList<Bitmap>

    // font
    var assetMgr: AssetManager

    // TODO: Set Sprite image

    constructor(holder: SurfaceHolder, context: Context, screenWidth: Int, screenHeight: Int) : super() {
        this.holder = holder
        this.context = context

        this.screenWidth = screenWidth
        this.screenHeight = screenHeight

        player = Player(screenWidth/2f, screenHeight/2f, 0, 50, true)
        enemies = Enemies()

        matrixRelfect = Matrix()
        matrixRelfect.setValues(floatArrayOf(-1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f))

        this.joyStick = JoyStick(rightJoyStick, screenWidth, screenHeight)

        // Load Sprite images
        bitmapItems = arrayListOf()
        this.bitmapBackground = BitmapFactory.decodeResource(context.resources, R.drawable.sprite_img_background)
        loadBitmaps(context)

        assetMgr = context.assets

        // TODO: Set SpriteAnimation
    }

    override fun run() {
        super.run()

        var canvas:Canvas? = null

        while (true) {
            canvas = holder.lockCanvas(null)
            try {
                // Thread 간 Heap 메모리 공유
                synchronized(holder) { // 해당객체 holder에 Lock이 걸린다

                    // 잔상 지우고 다시 그리기
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
                    drawBackground(canvas)
                    when (status) {
                        0 -> {
                           drawTitle(canvas)
                        }
                        1 -> {

                            generateEnemyFish()

                            for (idx in 0..enemies.enemyList.size-1) {
                                detectCollisionAndMove(canvas, idx)
                                drawEnemy(canvas, idx)
                                goneFish(idx)
                            }


                            changeWeightLevel()
                            drawPlayer(canvas)


                            drawInfo(canvas)
                            drawJoyStick(canvas)

                        }

                        2 -> {
                            drawPlayer(canvas)
                            drawPausedButton(canvas)
                            if (!paused) diedPlayer(canvas)
                        }

                    }

                }
            }
            finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas)
                }
            }
        }

    }

    fun drawTitle(canvas: Canvas) {
        var fontPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        fontPaint.setTypeface(Typeface.createFromAsset(assetMgr, "CookieRun Black.ttf"))
        fontPaint.textSize = 150f
        fontPaint.setColor(Color.WHITE)
        canvas.drawText("Get Bigger", screenWidth/2f - 345f, 355f, fontPaint)

        fontPaint.setColor(Color.rgb(248, 150, 0))
        canvas.drawText("Get Bigger", screenWidth/2f - 350f, 350f, fontPaint)
        fontPaint.setTypeface(Typeface.createFromAsset(assetMgr, "CookieRun Regular.ttf"))
        fontPaint.setColor(Color.WHITE)
        fontPaint.textSize = 80f
        canvas.drawText("터치해서 시작하세요!", screenWidth/2f - 320f, screenHeight/2f, fontPaint)
    }

    fun detectCollisionAndMove(canvas: Canvas, idx: Int) {
            var isCollition = false
            var enemy = enemies.get(idx)
            var eX = enemy.x
            var eY = enemy.y
            var eLevel = enemy.weightLevel
            var eWidth = enemies.getImgSize(eLevel)
            var eHeight = eWidth
            if (eLevel == 7) eHeight /= 2
            var pSide = player.getImgSize(player.weightLevel)
            // enemy의 꼭짓점이 player의 너비 안에 있는가
            if (!isCollition && eX + eWidth >= player.x && eX <= player.x + pSide &&
                    eY + eHeight >= player.y && eY <= player.y + pSide) {
                        isCollition = true
            }

            if (isCollition) {
                // 나보다 작은 물고기 먹었을 때
                if (player.weightLevel >= enemy.weightLevel) {
                    // destroy EnemyFish
                    player.totalScore += enemy.aimScore
                    if (player.totalScore >= this.highScore) {
                        this.highScore = player.totalScore
                    }
                    enemies.get(idx).alive = false


                    var fontPaint = Paint(Paint.ANTI_ALIAS_FLAG)
                    fontPaint.setTypeface(Typeface.createFromAsset(assetMgr, "CookieRun Black.ttf"))
                    fontPaint.textSize = 100f
                    fontPaint.setColor(Color.WHITE)
                    canvas.drawText(enemies.get(idx).aimScore.toString(), eX, eY, fontPaint)
                }
                // 나보다 큰 물고기 먹었을 때
                else {
                    // TODO: Thread로 life 구현
//                    if (--life <= 0) {
                        player.alive = false
                        highScore = player.totalScore
                        status = 2
//                    }
                }

            }
            else {
                var step = enemy.speed
                if (enemy.direction) step *= -1
                enemies.get(idx).move(step, 0)
            }

    }


    fun goneFish(idx: Int) {
        var enemy = enemies.get(idx)
        var eX = enemy.x

        if (enemy.direction && eX+enemies.getImgSize(enemy.weightLevel) <= 0 ||
                !enemy.direction && eX >= screenWidth) {
            enemies.get(idx).alive = false
        }
    }

    fun generateEnemyFish() {
        var level = makeWeightedRandomLevel()
        var posX = makeEnemyRandomX(level)
        var posY = makeEnemyRandomY(level)
        var speed = enemies.getSpeed(level)
        if (enemies.enemyList.size < enemies.numMaxFish) {
            enemies.addFish(Enemy(posX, posY, level, speed, true))
        }
        for (idx in 0..enemies.enemyList.size-1) {
            level = makeWeightedRandomLevel()
            posX = makeEnemyRandomX(level)
            posY = makeEnemyRandomY(level)
            speed = enemies.getSpeed(level)
            if (!enemies.get(idx).alive) {
                enemies.get(idx).x = posX
                enemies.get(idx).y = posY
                enemies.get(idx).weightLevel = level
                enemies.get(idx).speed = speed
                enemies.get(idx).aimScore = enemies.getAimScore(level)
                enemies.get(idx).alive = true
            }
        }
    }

    fun makeWeightedRandomLevel(): Int {
        var random = Random().nextInt(100)
        var result = 0
        enemies.rotateWeight(player.weightLevel)
        for (idx in 0..enemies.prob.size-1) {
            random -= enemies.prob.get(idx)
            if (random < 1) {
                result = idx
                break
            }
        }
        return result
    }

    fun makeEnemyRandomX(level: Int) : Float {
        var direction = Random().nextInt(2)
        var result = 0f
        Log.d(TAG, "direction: $direction")
        if (direction == 0) {
            result = 0f-enemies.getImgSize(level)
        }
        else {
            result = screenWidth.toFloat()
        }
        return result
    }

    fun makeEnemyRandomY(level: Int) : Float {
        return Random().nextInt(screenHeight - enemies.getImgSize(level) - 50) + 50f
    }

    fun changeWeightLevel() : Boolean{
        if (player.totalScore >= player.getAimScore(player.weightLevel) && player.weightLevel<7) {
            player.weightLevel += 1
        }
        // TODO: level up
//        if (player.weightLevel >= 8) {
//            level += 1
//            player.x = screenWidth/2f
//            player.y = screenHeight/2f
//            player.weightLevel = 0
//            player.speed = 50
//            return true // 스테이지 레벨 업
//        }

        return false
    }

    fun drawBackground(canvas: Canvas) {
        var backPaint: Paint = Paint()
        backPaint.setColor(Color.rgb(102, 204, 255))
        canvas.drawRect(0f, 0f, screenWidth.toFloat(), screenHeight.toFloat(), backPaint)

        var posX: Int = 0
        var posY: Int = screenHeight - (screenHeight / 3)
        var srcArea: Rect = Rect(0, 0, bitmapBackground.width, bitmapBackground.height)
        var destArea: Rect = Rect(posX, posY, posX+screenWidth, posY+screenHeight/3)
        canvas.drawBitmap(bitmapBackground, srcArea, destArea, null)
    }

    fun drawJoyStick(canvas: Canvas) {
        canvas.drawCircle(joyStick.backPosX, joyStick.backPosY, 150f, joyStick.backPaint)
        canvas.drawCircle(joyStick.frontPosX, joyStick.frontPosY, 75f, joyStick.frontPaint)
    }

    fun diedPlayer(canvas: Canvas) {
        var fontPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        fontPaint.setTypeface(Typeface.createFromAsset(assetMgr, "CookieRun Black.ttf"))
        fontPaint.textSize = 150f
        fontPaint.setColor(Color.WHITE)
        canvas.drawText("GAME OVER", screenWidth/2f - 390f, 355f, fontPaint)

        if (player.y > -60f) {
            player.move(0, -2)
        }
        else {
            // TODO: ranking 보여주기
        }
    }

    fun drawPausedButton(canvas: Canvas) {
        var fontPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        fontPaint.setTypeface(Typeface.createFromAsset(assetMgr, "CookieRun Regular.ttf"))
        fontPaint.textSize = 70f
        fontPaint.setColor(Color.WHITE)
        canvas.drawText("다시하기", screenWidth/2f-350f, screenHeight/2f, fontPaint)
        canvas.drawText("메인으로", screenWidth/2f+100f, screenHeight/2f, fontPaint)


        var bitmapRetry: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.btn_retry)
        var bitmapHome: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.btn_home)
        var srcArea: Rect = Rect(0, 0, bitmapRetry.width, bitmapRetry.height)
        var destAreaRetry: Rect = Rect(screenWidth/2-300, screenHeight/2+50, screenWidth/2-150, screenHeight/2+150+50)
        var destAreaHome: Rect = Rect(screenWidth/2+150, screenHeight/2+50, screenWidth/2+300, screenHeight/2+150+50)
        canvas.drawBitmap(bitmapRetry, srcArea, destAreaRetry, null)
        canvas.drawBitmap(bitmapHome, srcArea, destAreaHome, null)
    }

    fun drawPlayer(canvas: Canvas) {
        var pWeight = player.weightLevel
        var pWidth = player.getImgSize(pWeight)
        var posX = player.x
        var posY = player.y
        Log.d(TAG, "플레이어 상태 : lv$pWeight")

        var bitmap: Bitmap = player.bitmaps[pWeight][0]
        var srcArea: Rect = Rect(0, 0, bitmap.width, bitmap.height)
        var destArea: Rect = Rect(posX.toInt(), posY.toInt(), posX.toInt()+pWidth, posY.toInt()+pWidth)

        // if require reflect image
        if (!player.direction) {
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrixRelfect, true)
        }

        if (!player.alive) {
            bitmap = player.bitmaps[8][0]
            srcArea = Rect(0, 0, bitmap.width, bitmap.height)
        }

        canvas.drawBitmap(bitmap, srcArea, destArea, null)
    }

    fun drawEnemy(canvas: Canvas, idx:Int) {
        var eWeight = enemies.enemyList.get(idx).weightLevel
        var eWidth = enemies.getImgSize(eWeight)
        var posX = enemies.enemyList.get(idx).x
        var posY = enemies.enemyList.get(idx).y
        Log.d(TAG, "적 상태 : lv$eWeight")

        var bitmap: Bitmap = enemies.bitmaps[eWeight][0]
        var srcArea: Rect = Rect(0, 0, bitmap.width, bitmap.height)
        var destArea: Rect = Rect(posX.toInt(), posY.toInt(), posX.toInt()+eWidth, posY.toInt()+eWidth)
        if (eWeight == 7) destArea = Rect(posX.toInt(), posY.toInt(), posX.toInt()+eWidth, posY.toInt()+eWidth/2)

        // if require reflect image
        if (!enemies.get(idx).direction) {
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrixRelfect, true)
        }

        canvas.drawBitmap(bitmap, srcArea, destArea, null)
    }

    fun drawInfo(canvas: Canvas) {
        var fontPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        fontPaint.setTypeface(Typeface.createFromAsset(assetMgr, "CookieRun Regular.ttf"))
        fontPaint.textSize = 50f
        fontPaint.setColor(Color.WHITE)
        canvas.drawText("Score: ${player.totalScore}", 50f, 55f, fontPaint)
        canvas.drawText("Level: ${level}", 50f, 155f, fontPaint)
//        canvas.drawText("Life: ${life}", 50f, 300f, fontPaint)
        canvas.drawText("최고 점수: ${player.totalScore}", screenWidth/2-150f, 55f, fontPaint)

        var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.btn_pause)
        var srcArea: Rect = Rect(0, 0, bitmap.width, bitmap.height)
        var destArea: Rect = Rect(screenWidth-150, 50, screenWidth-150+100, 50+100)
        canvas.drawBitmap(bitmap, srcArea, destArea, null)
    }


    fun loadBitmaps(context: Context) {
        var playerLv1: MutableList<Bitmap> = arrayListOf(
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l1_1),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l1_2),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l1_3),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l1_4)
        )
        var playerLv2: MutableList<Bitmap> = arrayListOf(
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l2_1),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l2_2),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l2_3),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l2_4)
        )
        var playerLv3: MutableList<Bitmap> = arrayListOf(
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l3_1),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l3_2),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l3_3),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l3_4)
        )
        var playerLv4: MutableList<Bitmap> = arrayListOf(
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l4_1),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l4_2),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l4_3),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l4_4)
        )
        var playerLv5: MutableList<Bitmap> = arrayListOf(
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l5_1),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l5_2),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l5_3),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l5_4)
        )
        var playerLv6: MutableList<Bitmap> = arrayListOf(
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l6_1),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l6_2),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l6_3),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l6_4)
        )
        var playerLv7: MutableList<Bitmap> = arrayListOf(
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l7_1),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l7_2),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l7_3),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l7_4)
        )
        var playerLv8: MutableList<Bitmap> = arrayListOf(
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l8_1),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l8_2),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l8_3),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_l8_4)
        )
        var playerDie: MutableList<Bitmap> = arrayListOf(
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_die_1),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_die_2),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_player_die_3)
        )

        var enemyLv1: MutableList<Bitmap> = arrayListOf(
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_l1)
        )
        var enemyLv2: MutableList<Bitmap> = arrayListOf(
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_l2_1),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_l2_2),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_l2_3),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_l2_4)
        )
        var enemyLv3: MutableList<Bitmap> = arrayListOf(
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_l3_1),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_l3_2),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_l3_3),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_l3_4)
        )
        var enemyLv4: MutableList<Bitmap> = arrayListOf(
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_l4_1),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_l4_2),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_l4_3),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_l4_4)
        )
        var enemyLv5: MutableList<Bitmap> = arrayListOf(
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_l5_1),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_l5_2),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_l5_3),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_l5_4)
        )
        var enemyLv6: MutableList<Bitmap> = arrayListOf(
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_l6_1),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_l6_2),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_l6_3),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_l6_4)
        )
        var enemyLv7: MutableList<Bitmap> = arrayListOf(
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_l7_1),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_l7_2),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_l7_3),
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_l7_4)
        )
        var enemyLv8: MutableList<Bitmap> = arrayListOf(
                BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_l8)
        )

        var itemClam = BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_benefit_clam)
        var itemStar = BitmapFactory.decodeResource(context.resources, R.drawable.sprite_enemy_benefit_star)

        player.bitmaps.add(playerLv1)
        player.bitmaps.add(playerLv2)
        player.bitmaps.add(playerLv3)
        player.bitmaps.add(playerLv4)
        player.bitmaps.add(playerLv5)
        player.bitmaps.add(playerLv6)
        player.bitmaps.add(playerLv7)
        player.bitmaps.add(playerLv8)
        player.bitmaps.add(playerDie)
        enemies.bitmaps.add(enemyLv1)
        enemies.bitmaps.add(enemyLv2)
        enemies.bitmaps.add(enemyLv3)
        enemies.bitmaps.add(enemyLv4)
        enemies.bitmaps.add(enemyLv5)
        enemies.bitmaps.add(enemyLv6)
        enemies.bitmaps.add(enemyLv7)
        enemies.bitmaps.add(enemyLv8)
        bitmapItems.add(itemClam)
        bitmapItems.add(itemStar)
    }
}
