package com.green.musicapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

class Menu1Fragment : Fragment() {
    private val TAG:String = "Menu1Fragment"

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val rootView:View = inflater.inflate(R.layout.fragment_menu1, container, false)
        return rootView
    }

}