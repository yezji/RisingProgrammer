package com.green.musicapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.os.Debug as Debug1

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private val TAG:String = "MainActivity"
    val showToast:Boolean = true
    var musicIndex = 1

    // Activity에서 바인드할 Service의 레퍼런스를 저장할 변수
    var mService:MusicService? = null
    // bound 상태 저장 변수
    private var mBound:Boolean = false

    companion object {
        var db:AppDatabase? = null
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)

        val tvPlayBarNowTitle:TextView = findViewById(R.id.tvPlayBarNowTitle)
        val tvPlayBarNowArtist:TextView = findViewById(R.id.tvPlayBarNowTitle)

        outState.putString("title", tvPlayBarNowTitle.text.toString())
        outState.putString("aritist", tvPlayBarNowArtist.text.toString())
        outState.putInt("now_index", musicIndex)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO : Activity 생성 시 -> 초기화 처리와 View 생성(setContentView) 등 처리
        setContentView(R.layout.activity_main)

        if (showToast) Toast.makeText(this, "$TAG.onCreate()", Toast.LENGTH_SHORT).show()

        val btnStartMiniBar:ToggleButton = findViewById(R.id.btnPlayBarStart)
        val tvPlayBarNowTitle:TextView = findViewById(R.id.tvPlayBarNowTitle)
        val tvPlayBarNowArtist:TextView = findViewById(R.id.tvPlayBarNowTitle)

        val tvTitleRid:Int = resources.getIdentifier("chart_song_name_$musicIndex", "string", packageName)
        val tvArtistRid:Int = resources.getIdentifier("chart_song_artist_$musicIndex", "string", packageName)
        val musicRid:Int = resources.getIdentifier("sound_$musicIndex", "raw", packageName)

        if (savedInstanceState != null) {
            tvPlayBarNowTitle.setText(savedInstanceState.getString("title"))
            tvPlayBarNowArtist.setText(savedInstanceState.getString("artist"))
            musicIndex = savedInstanceState.getInt("now_index")
        }



        val bnv = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bnv.setOnNavigationItemSelectedListener(this)

        // bnv backstack init
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, Menu1Fragment(), "home")
        transaction.addToBackStack("home") // replace이후 && commit 전에 삽입 해야 함
        transaction.commit()
        // track backstack icon
        bnv.menu.findItem(R.id.menu_1_home).isChecked = true
    }

    override fun onRestart() {
        super.onRestart()
        // TODO : Activity 재시작 할 때 -> 보통은 아무것도 하지 않아도 된다
        if (showToast) Toast.makeText(this, "$TAG.onRestart()", Toast.LENGTH_SHORT).show()

        // TODO : 실행중인 노래가 있다면 노래 Activity 보여주기
    }

    override fun onStart() {
        super.onStart()
        // TODO : Activity 아직 표시X ->통신이나 센서 처리를 시작
        if (showToast) Toast.makeText(this, "$TAG.onStart()", Toast.LENGTH_SHORT).show()

        if (mService == null) {
            // Service 객체 없다면?  create를 하고 onStartCommand를 호출
            // 있다면? onStartCommand만 호출
            val intent = Intent(this, MusicService::class.java)
            startService(intent)

            // Service 객체의 참조 값 얻기 위해 연결하는 메서드 호출
            bindService(intent, mConnection, 0) // 0 : 서비스 객체를 자동으로 만들지 않음
        }
    }

    override fun onResume() {
        super.onResume()
        // TODO : Activity 최전면 표시 상태 -> 필요한 애니메이션 실행 등의 호면 갱신 처리
        if (showToast) Toast.makeText(this, "$TAG.onResume()", Toast.LENGTH_SHORT).show()

        val btnStartMiniBar:ToggleButton = findViewById(R.id.btnPlayBarStart)

        btnStartMiniBar.setOnClickListener {
            var flag:Boolean = btnStartMiniBar.isChecked
            if (flag) {
                mService!!.musicStop()
            }
            else {
                mService!!.musicStart()
            }
        }
    }

    // Service 연결상태 관리 객체
    val mConnection:ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // 서비스로부터 연결된 IBinder객체을 통해 넘어온 서비스 객체의 참조 값 얻기
            var binder = service as MusicService.LocalBinder
            mService = binder.getService()
            // 서비스 연결 처리
            mBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            // 예기치 않은 종료 시 서비스 연결 끊어짐 처리
            mBound = false
        }
    }

    override fun onPause() {
        super.onPause()
        // TODO : Activity가 일부만 표시되는 일시정지 상태 -> 애니메이션 등 화면 갱신 처리를 정지 또는 일시정지할 때 필요없는 리소스를 해제하거나 필요한 데이터를 영속화
        if (showToast) Toast.makeText(this, "$TAG.onPause()", Toast.LENGTH_SHORT).show()

        // 앱을 실행했을 때, 이미 노래가 재생중이면 해당 서비스의 정보를 받아오기
        // 참고 : https://jaeryo2357.tistory.com/31
        Intent(this, MusicService::class.java).also {
                intent -> bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        // TODO : Activity 아예 가려져서 정지된 상태 -> 통신이나 센서 처리를 정지
        if (showToast) Toast.makeText(this, "$TAG.onStop()", Toast.LENGTH_SHORT).show()

        // Activity 가려졌을 때 서비스 연결 해제 처리
        if (mBound) {
            unbindService(mConnection)
            mBound = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // TODO : Activity 폐기할 때 -> 필요없는 리소스 해제, Activity 참조는 모두 정리
        // TODO : Activity가 폐기되면 가비지컬렉션이 메모리 영역에서 해제하지만, Activity의 인스턴스가 다른 클래스에서 참조되고 있을 때는 폐기된 후에도 메모리에 남아 결국 메모리 누수가 발생하게 된다.
        if (showToast) Toast.makeText(this, "$TAG.onDestroy()", Toast.LENGTH_SHORT).show()

        android.os.Debug.stopMethodTracing() // 쓰레드 해제
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Fragment BackStack 관리 위한 코드 포함
        // 참고 : https://hwanine.github.io/android/backstack2/
        val sfm = supportFragmentManager
        val transaction: FragmentTransaction = sfm.beginTransaction()

        when(item.itemId) {
            R.id.menu_1_home -> {
                sfm.popBackStackImmediate("home", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                val fragmentHome = Menu1Fragment()
                transaction.replace(R.id.fragmentContainer, fragmentHome, "home")
                transaction.addToBackStack("home") // replace이후 && commit 전에 삽입 해야 함
            }
            R.id.menu_2_chart -> {
                sfm.popBackStackImmediate("chart", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                val fragmentChart = Menu2Fragment()
                transaction.replace(R.id.fragmentContainer, fragmentChart, "chart")
                transaction.addToBackStack("chart")
            }
            R.id.menu_3_cast -> {
                sfm.popBackStackImmediate("cast", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                val fragmentCast = Menu3Fragment()
                transaction.replace(R.id.fragmentContainer, fragmentCast, "cast")
                transaction.addToBackStack("cast")
            }
            R.id.menu_4_search -> {
                sfm.popBackStackImmediate("search", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                val fragmentSearch = Menu4Fragment()
                transaction.replace(R.id.fragmentContainer, fragmentSearch, "search")
                transaction.addToBackStack("search")
            }
            R.id.menu_5_drawer -> {
                sfm.popBackStackImmediate("drawer", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                val fragmentDrawer = Menu5Fragment()
                transaction.replace(R.id.fragmentContainer, fragmentDrawer, "drawer")
                transaction.addToBackStack("drawer")
            }
        }

        // Fragment들의 BackStack 수동으로 저장
        transaction.commit()
        transaction.isAddToBackStackAllowed

        return true
    }

    // Fragment BackStack에 따라 BottomNavigationView icon 바꿔주기
    private fun updateBottomMenu(nav: BottomNavigationView) {
        val tag1: Fragment? = supportFragmentManager.findFragmentByTag("home")
        val tag2: Fragment? = supportFragmentManager.findFragmentByTag("chart")
        val tag3: Fragment? = supportFragmentManager.findFragmentByTag("cast")
        val tag4: Fragment? = supportFragmentManager.findFragmentByTag("search")
        val tag5: Fragment? = supportFragmentManager.findFragmentByTag("drawer")

        if (tag1 != null && tag1.isVisible) nav.menu.findItem(R.id.menu_1_home).isChecked = true
        if (tag2 != null && tag2.isVisible) nav.menu.findItem(R.id.menu_2_chart).isChecked = true
        if (tag3 != null && tag3.isVisible) nav.menu.findItem(R.id.menu_3_cast).isChecked = true
        if (tag4 != null && tag4.isVisible) nav.menu.findItem(R.id.menu_4_search).isChecked = true
        if (tag5 != null && tag5.isVisible) nav.menu.findItem(R.id.menu_5_drawer).isChecked = true
    }
    // 뒤로가기 버튼이 눌렸을 때 updateBottomMenu로 icon 바꿔주기
    override fun onBackPressed() {
        super.onBackPressed()

        val bnv = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        updateBottomMenu(bnv)
    }

}
