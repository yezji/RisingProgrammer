package com.green.stockinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private var TAG: String = javaClass.simpleName
    lateinit var bnv: BottomNavigationView

    var fragmentHome = HomeFragment()
    var fragmentStock = StockFragment()
    var fragmentUser = UserFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bnv = findViewById(R.id.bottomNavigationView)
        bnv.setOnNavigationItemSelectedListener(this)

        // init backstack bnv
        var transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, HomeFragment(), "home")
        transaction.commit()
        // check backstack icon
        bnv.menu.findItem(R.id.menu_home).isChecked = true


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_searchview, menu)
        var searchItem: MenuItem? = menu?.findItem(R.id.actionSearch)
        var searchView: SearchView = searchItem?.actionView as SearchView

        searchView.queryHint = "종목을 검색하세요."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                var str: String = p0?:""
                var fragment: HomeFragment = supportFragmentManager.findFragmentById(R.id.frameLayout) as HomeFragment
                fragment.searchKeyword = str
                fragment.searchNews(str, 1, 100)
                return false // 키보드 내려감
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false // 키보드 내려감
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var transaction = supportFragmentManager.beginTransaction()
        supportActionBar?.show()
        var mTAG: String = ""
        when(item.itemId) {
            R.id.menu_home -> {
                mTAG = "home"
//                var fragmentHome = HomeFragment()
                supportFragmentManager.popBackStackImmediate(mTAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                transaction.replace(R.id.frameLayout, fragmentHome, mTAG)
            }
            R.id.menu_stock -> {
                mTAG = "stock"
//                var fragmentStock = StockFragment()
                supportFragmentManager.popBackStackImmediate(mTAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                transaction.replace(R.id.frameLayout, fragmentStock, mTAG)
            }
            R.id.menu_user -> {
                mTAG = "user"
//                var fragmentUser = UserFragment()
                supportFragmentManager.popBackStackImmediate(mTAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                transaction.replace(R.id.frameLayout, fragmentUser, mTAG)
                supportActionBar?.hide()
            }
        }

        transaction.addToBackStack(mTAG)
        transaction.commit()
        transaction.isAddToBackStackAllowed

        return true
    }

    private fun updateBottomMenu(bnv: BottomNavigationView) {
        val tag1: Fragment? = supportFragmentManager.findFragmentByTag("home")
        val tag2: Fragment? = supportFragmentManager.findFragmentByTag("stock")
        val tag3: Fragment? = supportFragmentManager.findFragmentByTag("user")

        if (tag1 != null && tag1.isVisible) bnv.menu.findItem(R.id.menu_home).isChecked = true
        if (tag2 != null && tag2.isVisible) bnv.menu.findItem(R.id.menu_stock).isChecked = true
        if (tag3 != null && tag3.isVisible) bnv.menu.findItem(R.id.menu_user).isChecked = true
    }

    // 뒤로가기 버튼이 눌렸을 때 updateBottomMenu로 icon 바꿔주기
    override fun onBackPressed() {
        super.onBackPressed()

        updateBottomMenu(bnv)
    }

}