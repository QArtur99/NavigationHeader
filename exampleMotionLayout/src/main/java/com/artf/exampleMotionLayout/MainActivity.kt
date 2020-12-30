package com.artf.exampleMotionLayout

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.artf.exampleMotionLayout.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val colorEvaluator = ArgbEvaluator()
    private var contentList = mutableListOf<View>()
    private var isMenuCollapsed: Boolean = false

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        val h1 = binding.motionLayout.h1
        val h2 = binding.motionLayout.h2
        val h3 = binding.motionLayout.h3
        val h4 = binding.motionLayout.h4

        setHeaderListener(h1, R.id.s2, R.id.s1, R.color.statusBar1, R.color.content1)
        setHeaderListener(h2, R.id.s3, R.id.s1, R.color.statusBar2, R.color.content2)
        setHeaderListener(h3, R.id.s4, R.id.s1, R.color.statusBar3, R.color.content3)
        setHeaderListener(h4, R.id.s5, R.id.s1, R.color.statusBar4, R.color.content4)

        contentList.apply {
            add(binding.content.c1)
            add(binding.content.c2)
            add(binding.content.c3)
            add(binding.content.c4)
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
                binding.motionLayout.motionLayout.setTransition(outAnim, inAnim)
                expand()
            } else {
                binding.motionLayout.motionLayout.setTransition(inAnim, outAnim)
                collapse(statusBarColor, contentColor)
            }
            binding.motionLayout.motionLayout.transitionToEnd()
            isMenuCollapsed = !isMenuCollapsed
        }
    }

    private fun collapse(statusBarColor: Int, contentColor: Int) {
        binding.motionLayout.arrow.isActivated = true
        animateStatusBar(window, "statusBarColor", window.statusBarColor, statusBarColor)
        for (view in contentList) {
            val colorDrawable = view.background as ColorDrawable
            animateStatusBar(view, "backgroundColor", colorDrawable.color, contentColor)
        }
    }

    private fun expand() {
        binding.motionLayout.arrow.isActivated = false
        animateStatusBar(window, "statusBarColor", window.statusBarColor, R.color.statusBar1)
    }

    private fun animateStatusBar(target: Any, propertyName: String, fromColor: Int, color: Int) {
        val objectAnimator = ObjectAnimator.ofObject(target, propertyName, colorEvaluator, 0, 0)
        objectAnimator.setObjectValues(fromColor, ContextCompat.getColor(this, color))
        objectAnimator.duration = 600
        objectAnimator.start()
    }
}