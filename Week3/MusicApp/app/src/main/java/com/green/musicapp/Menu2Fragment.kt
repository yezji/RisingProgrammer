package com.green.musicapp

import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class Menu2Fragment : Fragment() {
    private val TAG:String = "Menu2Fragment"

    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseUser: FirebaseUser? = null
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference

    private var songList:MutableList<Song> = mutableListOf()
    private var selectedList: SparseBooleanArray = SparseBooleanArray()
    private var hashtagList:MutableList<String> = mutableListOf()

    lateinit var rv:RecyclerView
    lateinit var adapter:ChartAdapter
    lateinit var btnAddToPlaylist:Button

    private var callbackListener:ClickCallbackListener = object : ClickCallbackListener {
        override lateinit var selectedList: SparseBooleanArray

        override fun callBack(pos: Int, selectedList:SparseBooleanArray) {
//            Toast.makeText(view!!.context, "$pos 번째 아이템을 클릭했습니다.", Toast.LENGTH_SHORT).show()
            this.selectedList = selectedList
        }

    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view:View = inflater.inflate(R.layout.fragment_menu2, container, false)

        btnAddToPlaylist = view.findViewById(R.id.btnAddToPlaylist)


        // 어댑터 생성
        adapter = ChartAdapter(view.context, songList)

        // 리사이클러뷰 생성
        rv = view.findViewById(R.id.rvChartList)
        rv.setHasFixedSize(true)

        // 레이아웃 매니저 연결
        rv.layoutManager = LinearLayoutManager(activity)
        // 기본 구분선 추가
        var dividerItemDecoration:DividerItemDecoration = DividerItemDecoration(rv.context, LinearLayoutManager(activity).orientation)
        rv.addItemDecoration(dividerItemDecoration)


        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseRef = database!!.getReference("song_of_chart")

        // 데이터 로딩
        databaseRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                songList.clear()
                for (data in snapshot.children) {
                    var song = data.getValue(Song::class.java)
                    songList.add(song!!)
                }
                adapter.notifyDataSetChanged() // 리스트 저장 및 새로고침
            }

            override fun onCancelled(error: DatabaseError) {
                // db를 가져오던 중 에러 발생 시
                Log.e(TAG, "DB를 가져오던 중 에러가 발생했습니다." + error.toException().toString())
            }
        })

        // 리사이클러뷰에 어댑터 연결
        adapter.setCallbackListener(callbackListener)
        rv.adapter = adapter

        /*// TODO : 차트 리소스
        var ivBackground: ImageView = view.findViewById(R.id.ivBackground)
        var ivChartImg: ImageView = view.findViewById(R.id.ivChartImg)
        var tvChartThemeTitle: TextView = view.findViewById(R.id.tvChartThemeTitle)

        database!!.getReference("chart").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                hashtagList.clear()
                for (data in snapshot.children) {
                    var chart = data.getValue(Chart::class.java)
                    // TODO: listview 추가하기
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // db를 가져오던 중 에러 발생 시
                Log.e(TAG, "DB를 가져오던 중 에러가 발생했습니다." + error.toException().toString())
            }
        })*/


        btnAddToPlaylist.visibility = View.VISIBLE
        btnAddToPlaylist.setOnClickListener {
            var playlistRef = database.getReference("song_of_playlist")
            var count:Int = adapter.itemCount
            var cnt:Int = listOf(playlistRef).size

            var list = songList.toMutableList()

            if (count > 0) {
                for (idx in 0..count-1) {

                    if (selectedList.get(idx, false)) {
                        Log.d(TAG, list.get(idx).title.toString())

                        var tmpTitle = list.get(idx).title.toString()
                        var tmpArtist = list.get(idx).artist.toString()
                        var tmpAlbum = list.get(idx).img_album.toString()
                        var tmpSound = list.get(idx).url_sound.toString()

                        playlistRef.child("song_$cnt").child("title").setValue(tmpTitle)
                        playlistRef.child("song_$cnt").child("artist").setValue(tmpArtist)
                        playlistRef.child("song_$cnt").child("img_album").setValue(tmpAlbum)
                        playlistRef.child("song_$cnt").child("url_sound").setValue(tmpSound)

                        cnt += 1

                    }
                }
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        if (adapter.itemCount > 0) {
            btnAddToPlaylist.visibility = View.GONE
        }
        else {
            btnAddToPlaylist.visibility = View.VISIBLE
        }
    }
}
