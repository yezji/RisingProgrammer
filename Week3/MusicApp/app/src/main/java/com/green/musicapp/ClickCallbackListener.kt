package com.green.musicapp

import android.util.SparseBooleanArray

interface ClickCallbackListener {
    abstract var selectedList: SparseBooleanArray

    fun callBack(pos: Int, selectedList: SparseBooleanArray)
}