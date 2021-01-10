package com.green.musicapp

import androidx.room.*

@Entity(tableName = "tb_user")
data class User(
    @PrimaryKey(autoGenerate = false) var id:String,
    var pw:String
)
