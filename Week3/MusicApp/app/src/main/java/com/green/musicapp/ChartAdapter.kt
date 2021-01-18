package com.green.musicapp

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

// list를 RecyclerView에 바인딩(=데이터 갱신)하기 위한 사전 작업
class ChartAdapter(var context:Context, var songList: MutableList<Song>) : RecyclerView.Adapter<ChartAdapter.SongHolder>() {


    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    private var selectedList:SparseBooleanArray = SparseBooleanArray(0)

    private lateinit var callbackListener:ClickCallbackListener

    fun setCallbackListener(callbacklistener:ClickCallbackListener) {
        this.callbackListener = callbacklistener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_chart_song, parent, false)

        return SongHolder(itemView)
    }

    override fun onBindViewHolder(holder: SongHolder, position: Int) {
        // 생성된 holder에 값을 넣어줌
        holder.tvNameInChartOfSong.setText(songList.get(position).title)
        holder.tvArtistInChartOfSong.setText(songList.get(position).artist)
        Glide.with(holder.itemView)
            .load(songList.get(position).img_album)
            .into(holder.ivAlbumOfSong)


        // 뷰 재사용하기 때문에 바인됭되는 시점에서 배경색을 바꾸도록 처리
        if (selectedList.get(position, false)) {
            holder.itemView.setBackgroundColor(holder.itemView.resources.getColor(R.color.song_selected_gray))
            this.callbackListener.callBack(position, selectedList)
        }
        else {
            holder.itemView.setBackgroundColor(holder.itemView.resources.getColor(R.color.transparent))
            this.callbackListener.callBack(position, selectedList)
        }


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
//        var btnAddToPlaylist:Button


        init {
            tvNameInChartOfSong = itemView.findViewById(R.id.tvNameInChartOfSong)
            tvArtistInChartOfSong = itemView.findViewById(R.id.tvArtistInChartOfSong)
            ivAlbumOfSong = itemView.findViewById(R.id.ivAlbumOfSong)
            clItemSong = itemView.findViewById(R.id.clItemSong)
//            btnAddToPlaylist = chartView.findViewById(R.id.btnAddToPlaylist)

            clItemSong.setOnClickListener(View.OnClickListener {
                var position = adapterPosition

                if (selectedList.get(position, false)) {
                    selectedList.put(position, false)
                    notifyItemChanged(position)
                }
                else {
                    selectedList.put(position, true)
                    notifyItemChanged(position)

                }
            })


            /*if (itemCount > 0) {
                btnAddToPlaylist.visibility = View.VISIBLE
                btnAddToPlaylist.setText(itemCount.toString()+"개 담기")
            }
            else {
                btnAddToPlaylist.visibility = View.GONE
            }*/

        }

    }


}
