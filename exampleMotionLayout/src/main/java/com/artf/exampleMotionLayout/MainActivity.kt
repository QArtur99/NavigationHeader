package com.artf.exampleMotionLayout

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content.*
import kotlinx.android.synthetic.main.navigation_header.*

class MainActivity : AppCompatActivity() {

    private val colorEvaluator = ArgbEvaluator()
    private var contentList = mutableListOf<View>()
    private var isMenuCollapsed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setHeaderListener(h1, R.id.s2, R.id.s1, R.color.statusBar1, R.color.content1)
        setHeaderListener(h2, R.id.s3, R.id.s1, R.color.statusBar2, R.color.content2)
        setHeaderListener(h3, R.id.s4, R.id.s1, R.color.statusBar3, R.color.content3)
        setHeaderListener(h4, R.id.s5, R.id.s1, R.color.statusBar4, R.color.content4)

        contentList.apply {
            add(c1)
            add(c2)
            add(c3)
            add(c4)
        }
    }

    private fun setHeaderListener(
        navigationHeader: View,
        outAnim: Int,
        inAnim: Int,
        statusBarColor: Int,
        contentColor: Int
    ) {
        navigationHeader.setOnClickListener {
            if (isMenuCollapsed) {
                motionLayout.setTransition(outAnim, inAnim)
                expand()
            } else {
                motionLayout.setTransition(inAnim, outAnim)
                collapse(statusBarColor, contentColor)
            }
            motionLayout.transitionToEnd()
            isMenuCollapsed = !isMenuCollapsed
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
        objectAnimator.duration = 600
        objectAnimator.start()
    }
}