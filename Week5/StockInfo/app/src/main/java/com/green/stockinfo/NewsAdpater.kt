package com.green.stockinfo

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NewsAdpater : RecyclerView.Adapter<NewsAdpater.ViewHolder> {
    private lateinit var items: List<Items>

    constructor(items: List<Items>) : super() {
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdpater.ViewHolder {
        var itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsAdpater.ViewHolder, position: Int) {
        var item: Items = items.get(position)
        holder.setItem(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder : RecyclerView.ViewHolder {
        private lateinit var tvNewsTitle: TextView
        private lateinit var tvNewsDate: TextView

        constructor(itemView: View) : super(itemView) {
            tvNewsTitle = itemView.findViewById(R.id.tvNewsTitle)
            tvNewsDate = itemView.findViewById(R.id.tvNewsDate)
        }

        fun setItem(item: Items) {
            tvNewsTitle.setText(Html.fromHtml(item.title))
            tvNewsDate.setText(item.pubDate)
        }
    }
}
