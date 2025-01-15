package com.app

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.util.showToast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception
import kotlin.concurrent.thread

class NewsFragment(var newsType: String) : Fragment() {

    private lateinit var newsRecyclerView: RecyclerView

    private lateinit var refresh:SwipeRefreshLayout

    private val newsList = ArrayList<News>()

    private fun refresh() {
        thread {
            val request = Request.Builder()
                .url("http://v.juhe.cn/toutiao/index?type="+newsType+"&key=" + MyApplication.KEY).build()
            val response = OkHttpClient().newCall(request).execute()
            val json = response.body?.string()
            val newsResponse = Gson().fromJson(json, NewsResponse::class.java)
            if (newsResponse?.result != null) {
                try {
                    val data = newsResponse.result.data
                    newsList.clear()
                    newsList.addAll(data)
                    activity?.runOnUiThread {
                        newsRecyclerView.adapter?.notifyDataSetChanged()
                    }
                }catch (e:Exception){
                    activity?.runOnUiThread {
                        "请检查网络接口是否正常".showToast()
                    }
                }

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        newsRecyclerView = view.findViewById<RecyclerView>(R.id.news_recycler_view)

        refresh = view.findViewById(R.id.news_refresh)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        newsRecyclerView.layoutManager = LinearLayoutManager(MyApplication.context)

        newsRecyclerView.adapter = NewsAdapter(newsList)

        refresh()

        refresh.setColorSchemeColors(Color.parseColor("#03A9F4"))
        refresh.setOnRefreshListener {
            thread {
                Thread.sleep(700)
                activity?.runOnUiThread {
                    refresh()
                    refresh.isRefreshing = false
                }
            }
        }
    }

    //把它作为MainActivity的内部类，就能用this当context了
    inner class NewsAdapter(private val newsList: List<News>) :
        RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

        inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.news_title)
            val description: TextView = itemView.findViewById(R.id.news_desc)
            val image: com.makeramen.roundedimageview.RoundedImageView =
                itemView.findViewById(R.id.news_image)
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
            val itemView =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.news_item_one_image, parent, false)
            return NewsViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
            val news = newsList[position]

            //视图与内容绑定
            holder.title.text = news.title
            holder.description.text = news.author_name
            //图片加载
            Glide.with(MyApplication.context).load(news.thumbnail_pic_s).into(holder.image)

            holder.itemView.setOnClickListener {
                //holder.adapterPosition : 下标
                val intent = Intent(MyApplication.context, DetailActivity::class.java)
                intent.putExtra("url=", newsList[holder.adapterPosition].url)
                startActivity(intent)
            }
        }


        override fun getItemCount(): Int {
            return newsList.size
        }

    }
}