package com.green.musicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class PlaylistActivity : AppCompatActivity() {
    private val TAG:String = "PlaylistActivity"

    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseUser: FirebaseUser? = null
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference

    private var songList:MutableList<Song> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)

        /*val tvPlayBarNowTitle:TextView = findViewById(R.id.tvPlayBarNowTitle)
        val tvPlayBarNowArtist:TextView = findViewById(R.id.tvPlayBarNowArtist)

        // MainActivity에서 넘어온 정보 반영
        val getNowTitle:String? = intent.getStringExtra("now_title")
        val getNowArtist:String? = intent.getStringExtra("now_artist")
        tvPlayBarNowTitle.setText(getNowTitle)
        tvPlayBarNowArtist.setText(getNowArtist)*/


        // 어댑터 생성
        val adapter = PlaylistAdapter(songList)

        // 리사이클러뷰 생성
        val rv: RecyclerView = findViewById(R.id.rvPlaylist)
        rv.setHasFixedSize(true)
        // 레이아웃 매니저 연결
        rv.layoutManager = LinearLayoutManager(this)
        // 기본 구분선 추가
        var dividerItemDecoration: DividerItemDecoration = DividerItemDecoration(rv.context, LinearLayoutManager(applicationContext).orientation)
        rv.addItemDecoration(dividerItemDecoration)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseRef = database!!.getReference("song_of_playlist")

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
        rv.adapter = adapter

        // ItemTouchHelper 생성
        var helper = ItemTouchHelper(ItemTouchHelperCallback(adapter))


    }
}