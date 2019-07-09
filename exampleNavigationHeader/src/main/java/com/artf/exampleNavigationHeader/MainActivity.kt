package com.artf.exampleNavigationHeader

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.artf.navigationheader.Header
import com.artf.navigationheader.HeaderView
import com.artf.navigationheader.NavigationHeaderMotionLayout


class MainActivity : AppCompatActivity() {

    private val navigationHeader by lazy { findViewById<NavigationHeaderMotionLayout>(R.id.navigationHeader) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutInflater = LayoutInflater.from(this)
        val headerList = mutableListOf<HeaderView>()

        val titleList = mutableListOf<Header>().apply {
            add(Header("SERVICES", R.color.header1, R.color.statusBar1, R.color.content1))
            add(Header("AUTO", R.color.header2, R.color.statusBar2, R.color.content2))
            add(Header("JOB", R.color.header3, R.color.statusBar3, R.color.content3))
            add(Header("REALITY", R.color.header4, R.color.statusBar4, R.color.content4))
        }

        titleList.forEach {
            val headerView = layoutInflater.inflate(R.layout.header, null)
            headerView.findViewById<TextView>(R.id.title).text = it.title
            headerView.setBackgroundColor(ContextCompat.getColor(this, it.headerColor!!))
            headerList.add(HeaderView(headerView, it.headerColor, it.statusBarColor, it.contentColor))
        }

        val contentList = mutableListOf<View>().apply {
            add(findViewById(R.id.c1))
            add(findViewById(R.id.c2))
            add(findViewById(R.id.c3))
            add(findViewById(R.id.c4))
        }

        navigationHeader.arrow.setColorFilter(Color.BLACK)
        navigationHeader.arrow.imageAlpha = 0
        navigationHeader.initNavigationHeader(this, headerList, contentList)
    }
}