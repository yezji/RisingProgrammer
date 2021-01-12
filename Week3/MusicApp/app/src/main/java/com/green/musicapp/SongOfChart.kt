package com.green.musicapp

import androidx.room.*


@Entity(tableName = "tb_song_of_chart")
/*
// TODO : rid 인것을 db걸로 바꾸기
//  data class ItemSongOfChart(
    @PrimaryKey(autoGenerate = true) var song_id:Long,
    var album:ByteArray?,
    var title:String,
    var artist:String
)*/

data class SongOfChart(
    var album:Int,
    var title:Int,
    var artist:Int,
    var music:Int
)
