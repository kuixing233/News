package com.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.app.util.showToast
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    //top(推荐,默认)
    //guonei(国内)
    //guoji(国际)
    //yule(娱乐)
    //tiyu(体育)
    //junshi(军事)
    //keji(科技)
    //caijing(财经)
    //youxi(游戏)
    //qiche(汽车)
    //jiankang(健康)

    val newsTypeList = listOf("top", "guonei", "guoji", "yule", "keji")
    val titleList = listOf("社会", "国内", "国际", "娱乐", "科技")
    val fragmentList = ArrayList<NewsFragment>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabLayout = findViewById<TabLayout>(R.id.news_tab_layout)
        val viewPager = findViewById<ViewPager>(R.id.news_view_pager)

        for (newsType in newsTypeList) {
            fragmentList.add(NewsFragment(newsType))
        }
        viewPager.adapter = MyAdapter(supportFragmentManager)

        tabLayout.setupWithViewPager(viewPager)

    }

    inner class MyAdapter(fm:FragmentManager):FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getPageTitle(position: Int): CharSequence {
            return titleList[position]
        }

    }

}



