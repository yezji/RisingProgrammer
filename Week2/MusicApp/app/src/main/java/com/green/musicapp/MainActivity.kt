package com.green.musicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private val TAG:String = "MainActivity"
    val showToast:Boolean = true

    companion object {
        var db:AppDatabase? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO : Activity 생성 시 -> 초기화 처리와 View 생성(setContentView) 등 처리
        setContentView(R.layout.activity_main)

        if (showToast) Toast.makeText(this, "$TAG.onCreate()", Toast.LENGTH_SHORT).show()

        val btnStartMiniBar:ToggleButton = findViewById(R.id.btnPlayBarStart)

        btnStartMiniBar.setOnClickListener {
            var intent:Intent = Intent(this, PlayMusicService::class.java)

//            if (btnStartMiniBar )
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
    }

    override fun onStart() {
        super.onStart()
        // TODO : Activity 아직 표시X ->통신이나 센서 처리를 시작
        if (showToast) Toast.makeText(this, "$TAG.onStart()", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        // TODO : Activity 최전면 표시 상태 -> 필요한 애니메이션 실행 등의 호면 갱신 처리
        if (showToast) Toast.makeText(this, "$TAG.onResume()", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        // TODO : Activity가 일부만 표시되는 일시정지 상태 -> 애니메이션 등 화면 갱신 처리를 정지 또는 일시정지할 때 필요없는 리소스를 해제하거나 필요한 데이터를 영속화
        if (showToast) Toast.makeText(this, "$TAG.onPause()", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        // TODO : Activity 아예 가려져서 정지된 상태 -> 통신이나 센서 처리를 정지
        if (showToast) Toast.makeText(this, "$TAG.onStop()", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        // TODO : Activity 폐기할 때 -> 필요없는 리소스 해제, Activity 참조는 모두 정리
        // TODO : Activity가 폐기되면 가비지컬렉션이 메모리 영역에서 해제하지만, Activity의 인스턴스가 다른 클래스에서 참조되고 있을 때는 폐기된 후에도 메모리에 남아 결국 메모리 누수가 발생하게 된다.
        if (showToast) Toast.makeText(this, "$TAG.onDestroy()", Toast.LENGTH_SHORT).show()
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
