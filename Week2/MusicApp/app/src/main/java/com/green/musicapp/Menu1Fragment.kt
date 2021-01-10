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
    val showToast:Boolean = false

    var songOfChartList = mutableListOf<SongOfChart>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // TODO : Fragment가 Activity가 연결될 때
        if (showToast) Toast.makeText(activity, "$TAG.onAttach()", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO : Fragment 처음 생성 시
        //  -> Activity와 마찬가지로 레이아웃 등 리소스 초기화 처리
        super.onCreate(savedInstanceState)
        if (showToast) Toast.makeText(activity, "$TAG.onCreate()", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        // TODO : Fragment의 layout을 inflate하여 View 생성
        //  -> Fragment의 화면 전환, Fragment layout 처리
        if (showToast) Toast.makeText(activity, "$TAG.onCreateView()", Toast.LENGTH_SHORT).show()

        val rootView:View = inflater.inflate(R.layout.fragment_menu1, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO : Activity에서 Fragment를 모두 생성한 뒤 호출
        //  -> UI와 관련된 View 변경 작업 (View 초기화, View 생성(setContentView 호출) 등 처리
        if (showToast) Toast.makeText(activity, "$TAG.onActivityCreated()", Toast.LENGTH_SHORT).show()
    }

    // Fragment 생명주기에는 onRestart()가 없다!
/*    override fun onRestart() {
        super.onRestart()
    }*/

    override fun onStart() {
        super.onStart()
        // TODO : Activity와 마찬가지로 Fragment가 화면에 보이기 바로 직전에 호출
        if (showToast) Toast.makeText(activity, "$TAG.onStart()", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        // TODO : Activity와 마찬가지로 Fragment가 사용자와 상호작용을 하기 바로 직전에 호출
        if (showToast) Toast.makeText(activity, "$TAG.onResume()", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        // TODO : 다른 Fragment가 화면을 가리게 되어 기존 Fragment의 화면이 사라지게 되는 시점에서 호출, 기존 레이아웃은 백스택으로 들어감
        if (showToast) Toast.makeText(activity, "$TAG.onPause()", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        // TODO : Fragment가 화면에서 더 이상 보여지지 않게 되었을 때(기능을 완전히 상실했을 때) 호출
        if (showToast) Toast.makeText(activity, "$TAG.onStop()", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // TODO : Fragment의 View들의 리소르를 해제
        if (showToast) Toast.makeText(activity, "$TAG.onDestroyView()", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        // TODO : Fragment가 완전히 소멸될 때 호출
        if (showToast) Toast.makeText(activity, "$TAG.onDestroy()", Toast.LENGTH_SHORT).show()
    }

    override fun onDetach() {
        super.onDetach()
        // TODO : Fragment가 Activity와 연결이 끊기기 직전에 호출
        if (showToast) Toast.makeText(activity, "$TAG.onDetach()", Toast.LENGTH_SHORT).show()
    }

}