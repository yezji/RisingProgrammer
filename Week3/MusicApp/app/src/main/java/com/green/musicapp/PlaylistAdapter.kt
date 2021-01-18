package com.green.musicapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

// list를 RecyclerView에 바인딩(=데이터 갱신)하기 위한 사전 작업
class PlaylistAdapter(private var songList: MutableList<Song>) : RecyclerView.Adapter<PlaylistAdapter.SongHolder>(), ItemTouchHelperListener {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_playlist_song, parent, false)


        return SongHolder(itemView)
    }

    override fun onBindViewHolder(holder: SongHolder, position: Int) {
        // 생성된 holder에 값을 넣어줌
        holder.tvNameInChartOfSong.setText(songList.get(position).title)
        holder.tvArtistInChartOfSong.setText(songList.get(position).artist)
        Glide.with(holder.itemView)
            .load(songList.get(position).img_album)
            .into(holder.ivAlbumOfSong)

        holder.clItemSong.setOnClickListener {



        }
    }

    fun addSong(song:Song) {
        songList.add(song)
    }

    override fun getItemCount(): Int {
        // 안드로이드에게 사용할 데이터 개수 알려줌
        if (songList != null) {
            return songList.size
        }
        else {
            return 0
        }
    }

    inner class SongHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        // item 레이아웃을 클래스화해서 Holder에 던져주기
        var tvNameInChartOfSong:TextView
        var tvArtistInChartOfSong:TextView
        var ivAlbumOfSong:ImageView
        var clItemSong:ConstraintLayout


        init {
            tvNameInChartOfSong = itemView.findViewById(R.id.tvNameInChartOfSong)
            tvArtistInChartOfSong = itemView.findViewById(R.id.tvArtistInChartOfSong)
            ivAlbumOfSong = itemView.findViewById(R.id.ivAlbumOfSong)
            clItemSong = itemView.findViewById(R.id.clItemSong)

        }
    }

    override fun onItemMove(from_pos: Int, to_pos: Int): Boolean {
        // 이동할 객체 저장
        var song:Song = songList.get(from_pos)
        // 이동할 객체 삭제
        songList.removeAt(from_pos)
        // 이동하고 싶은 position에 추가
        songList.add(to_pos, song)

        // Adapter에 데이터 이동 알림
        notifyItemMoved(from_pos, to_pos)
        return true
    }

    override fun onItemSwipe(pos: Int) {
        songList.removeAt(pos)
        notifyItemRemoved(pos)
    }
}
