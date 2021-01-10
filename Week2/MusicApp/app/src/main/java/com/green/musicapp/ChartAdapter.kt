package com.green.musicapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// list를 RecyclerView에 바인딩(=데이터 갱신)하기 위한 사전 작업
class ChartAdapter : RecyclerView.Adapter<SongHolder>() {
    // 사용할 데이터를 초기화
    var listData = mutableListOf<ItemSongOfChart>()
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_chart_song, parent, false)

        return SongHolder(itemView)
    }

    override fun getItemCount(): Int {
        // 안드로이드에게 사용할 데이터 개수 알려줌
        return listData.size
    }

    // 생성된 holder에 값을 넣어줌
    override fun onBindViewHolder(holder: SongHolder, position: Int) {
        val song = listData.get(position)
        holder.setSong(song)
    }
}

// item 레이아웃을 클래스화해서 Holder에 던져주기
class SongHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {

    /*init {
        itemView.setOnClickListener {
        }
    }*/

    // item_chart_song.xml의 id에 직접 값을 설정
    fun setSong(song:ItemSongOfChart) {
        itemView.findViewById<ImageView>(R.id.ivAlbumOfSong).setImageResource(song.album)
        itemView.findViewById<TextView>(R.id.tvNameInChartOfSong).setText(song.title)
        itemView.findViewById<TextView>(R.id.tvArtistInChartOfSong).setText(song.artist)
    }
}