package com.artf.exampleNavigationHeader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.artf.exampleNavigationHeader.databinding.ActivityMainBinding
import com.artf.exampleNavigationHeader.databinding.HeaderBinding
import com.artf.navigationheader.Header
import com.artf.navigationheader.HeaderView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        val title1 = resources.getString(R.string.header1)
        val title2 = resources.getString(R.string.header2)
        val title3 = resources.getString(R.string.header3)
        val title4 = resources.getString(R.string.header4)
        val title5 = resources.getString(R.string.header5)
        val title6 = resources.getString(R.string.header6)
        val title7 = resources.getString(R.string.header7)

        val titleList = mutableListOf<Header>().apply {
            add(Header(title1, R.color.header1, R.color.statusBar1, R.color.content1))
            add(Header(title2, R.color.header2, R.color.statusBar2, R.color.content2))
            add(Header(title3, R.color.header3, R.color.statusBar3, R.color.content3))
            add(Header(title4, R.color.header4, R.color.statusBar4, R.color.content4))
            add(Header(title5, R.color.header5, R.color.statusBar5, R.color.content5))
            add(Header(title6, R.color.header6, R.color.statusBar6, R.color.content6))
            add(Header(title7, R.color.header7, R.color.statusBar7, R.color.content7))
        }

        val layoutInflater = LayoutInflater.from(this)
        val headerList = mutableListOf<HeaderView>()
        titleList.forEach {
            val headerView = HeaderBinding.inflate(layoutInflater)
            headerView.root.tag = it.title
            headerView.title.text = it.title
            headerView.root.setBackgroundColor(ContextCompat.getColor(this, it.headerColor!!))
            headerList.add(HeaderView(headerView.root, it.headerColor, it.statusBarColor, it.contentColor))
        }

        val contentList = mutableListOf<View>().apply {
            add(binding.content.c1)
            add(binding.content.c2)
            add(binding.content.c3)
            add(binding.content.c4)
        }

//        navigationHeader.arrow.setColorFilter(Color.BLACK)
//        navigationHeader.arrow.setImageDrawable(null)

        binding.navigationHeader.setOnCollapseListener {
            when(it.tag){
                title1 -> {}
                title2 -> {}
                title3 -> {}
                title4 -> {}
            }
        }

        binding.navigationHeader.initNavigationHeader(this, headerList, contentList)
    }
}