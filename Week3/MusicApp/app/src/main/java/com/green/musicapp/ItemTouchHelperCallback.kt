package com.green.musicapp

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemTouchHelperCallback(adapter: PlaylistAdapter) : ItemTouchHelper.Callback() {
    private lateinit var listener:ItemTouchHelperListener

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        var drag_flags = ItemTouchHelper.UP and ItemTouchHelper.DOWN
        var swipe_flags = ItemTouchHelper.START and ItemTouchHelper.END
        return makeMovementFlags(drag_flags, swipe_flags)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return listener.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        return listener.onItemSwipe(viewHolder.adapterPosition)
    }
}