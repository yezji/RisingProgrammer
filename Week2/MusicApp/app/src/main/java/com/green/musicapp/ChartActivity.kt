package com.green.musicapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        Toast.makeText(this, "ChartActivity.onCreate()", Toast.LENGTH_SHORT).show()
        // 1. 데이터 로딩
        val data = loadData()
        // 2. 어댑터 생성
        val adapter = ChartAdapter()
        // 3. 어댑터에 데이터 전달
        adapter.listData = data
        // 4. 화면에 있는 리사이클러뷰에 어댑터 연결
        val rv = findViewById<RecyclerView>(R.id.rvChartList)
        rv.adapter = adapter
        // 5. 레이아웃 매니저 연결
        rv.layoutManager = LinearLayoutManager(this)
    }

    fun loadData() : MutableList<ItemChartOfSong> {
        val data:MutableList<ItemChartOfSong> = mutableListOf()

        data.add(ItemChartOfSong(R.drawable.img_album_1, R.string.chart_song_name_1, R.string.chart_song_artist_1))
        data.add(ItemChartOfSong(R.drawable.img_album_2, R.string.chart_song_name_2, R.string.chart_song_artist_2))
        data.add(ItemChartOfSong(R.drawable.img_album_3, R.string.chart_song_name_3, R.string.chart_song_artist_3))
        data.add(ItemChartOfSong(R.drawable.img_album_4, R.string.chart_song_name_4, R.string.chart_song_artist_4))
        data.add(ItemChartOfSong(R.drawable.img_album_5, R.string.chart_song_name_5, R.string.chart_song_artist_5))
        data.add(ItemChartOfSong(R.drawable.img_album_6, R.string.chart_song_name_6, R.string.chart_song_artist_6))
        data.add(ItemChartOfSong(R.drawable.img_album_7, R.string.chart_song_name_7, R.string.chart_song_artist_7))
        data.add(ItemChartOfSong(R.drawable.img_album_8, R.string.chart_song_name_8, R.string.chart_song_artist_8))
        data.add(ItemChartOfSong(R.drawable.img_album_9, R.string.chart_song_name_9, R.string.chart_song_artist_9))
        data.add(ItemChartOfSong(R.drawable.img_album_10, R.string.chart_song_name_10, R.string.chart_song_artist_10))
        data.add(ItemChartOfSong(R.drawable.img_album_11, R.string.chart_song_name_11, R.string.chart_song_artist_11))
        data.add(ItemChartOfSong(R.drawable.img_album_12, R.string.chart_song_name_12, R.string.chart_song_artist_12))
        data.add(ItemChartOfSong(R.drawable.img_album_13, R.string.chart_song_name_13, R.string.chart_song_artist_13))
        data.add(ItemChartOfSong(R.drawable.img_album_14, R.string.chart_song_name_14, R.string.chart_song_artist_14))
        data.add(ItemChartOfSong(R.drawable.img_album_15, R.string.chart_song_name_15, R.string.chart_song_artist_15))
        data.add(ItemChartOfSong(R.drawable.img_album_16, R.string.chart_song_name_16, R.string.chart_song_artist_16))
        data.add(ItemChartOfSong(R.drawable.img_album_17, R.string.chart_song_name_17, R.string.chart_song_artist_17))
        data.add(ItemChartOfSong(R.drawable.img_album_18, R.string.chart_song_name_18, R.string.chart_song_artist_18))
        data.add(ItemChartOfSong(R.drawable.img_album_19, R.string.chart_song_name_19, R.string.chart_song_artist_19))

        return data
    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this, "ChartActivity.onRestart()", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "ChartActivity.onStart()", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "ChartActivity.onResume()", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this, "ChartActivity.onPause()", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this, "ChartActivity.onStop()", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "ChartActivity.onDestroy()", Toast.LENGTH_SHORT).show()
    }
}