package com.green.stockinfo

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private val TAG = javaClass.simpleName
    val naverAPI = NaverAPI.create()
    var searchKeyword: String = "증시"

    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: RecyclerView.Adapter<NewsAdpater.ViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.fragmentRecyclerView)
        var layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        searchNews(searchKeyword, 1, 100)

        return view
    }

    fun searchNews(query: String, start: Int, display: Int) {
        // callGetSearchNews
        naverAPI.getSearchNews(query, start, display)
            .enqueue(object : Callback<ResultGetSearchNews> {
                override fun onResponse(
                    call: Call<ResultGetSearchNews>,
                    response: Response<ResultGetSearchNews>
                ) {
                    var result = response.body()
                    var itemResult = result!!.items
                    mAdapter = NewsAdpater(itemResult)
                    recyclerView.adapter = mAdapter
                    Log.d("뉴스검색", "성공 : ${response.toString()}")
                }

                override fun onFailure(call: Call<ResultGetSearchNews>, t: Throwable) {
                    Log.d("뉴스검색", "실패 : $t")
                }
            })
    }
}