package com.green.musicapp

import androidx.room.*

// TODO : rid 인것을 db걸로 바꾸기

@Entity(tableName = "tb_song_of_chart")
/*data class ItemSongOfChart(
    @PrimaryKey(autoGenerate = true) var song_id:Long,
    var album:Int,
    var title:String,
    var artist:String
)*/
data class ItemSongOfChart(
//    @PrimaryKey(autoGenerate = true) var song_id:Long,
    var album:Int,
    var title:Int,
    var artist:Int
)