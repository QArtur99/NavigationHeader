package com.artf.sample

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val motionLayout by lazy { findViewById<NavigationHeaderMotionLayout>(R.id.motion_container) }
    private val colorEvaluator = ArgbEvaluator()
    private var contentList = mutableListOf<View>()
    private var arrow: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutInflater = LayoutInflater.from(this)
        val navigationHeader = layoutInflater.inflate(R.layout.navigation_header, null)
        motionLayout.addNavigationHeader(navigationHeader.rootView.findViewById(R.id.header))
        motionLayout.buildMotionScene()

        arrow = findViewById<View>(R.id.arrow)

//        setHeaderListener(v1, R.id.s2, R.id.s1, R.color.statusBar1, R.color.content1)
//        setHeaderListener(v2, R.id.s3, R.id.s1, R.color.statusBar2, R.color.content2)
//        setHeaderListener(v3, R.id.s4, R.id.s1, R.color.statusBar3, R.color.content3)
//        setHeaderListener(v4, R.id.s5, R.id.s1, R.color.statusBar4, R.color.content4)

        contentList.apply {
            add(findViewById(R.id.c1))
            add(findViewById(R.id.c2))
            add(findViewById(R.id.c3))
            add(findViewById(R.id.c4))
        }
    }

    private fun collapse(statusBarColor: Int, contentColor: Int) {
        arrow?.isActivated = true
        animateStatusBar(window, "statusBarColor", window.statusBarColor, statusBarColor)
        for (view in contentList) {
            val colorDrawable = view.background as ColorDrawable
            animateStatusBar(view, "backgroundColor", colorDrawable.color, contentColor)
        }
    }

    private fun expand() {
        arrow?.isActivated = false
        animateStatusBar(window, "statusBarColor", window.statusBarColor, R.color.statusBar1)
    }

    private fun animateStatusBar(target: Any, propertyName: String, fromColor: Int, color: Int) {
        val objectAnimator = ObjectAnimator.ofObject(target, propertyName, colorEvaluator, 0, 0)
        objectAnimator.setObjectValues(fromColor, ContextCompat.getColor(this, color))
        objectAnimator.duration = 700
        objectAnimator.start()
    }
}