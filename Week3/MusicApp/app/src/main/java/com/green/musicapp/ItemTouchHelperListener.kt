package com.green.musicapp

interface ItemTouchHelperListener {
    fun onItemMove(from_pos:Int, to_pos:Int) :Boolean
    fun onItemSwipe(pos:Int)
}