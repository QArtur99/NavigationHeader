package com.artf.navigationheader

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.ImageView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.constraintlayout.widget.ConstraintAttribute
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat

class NavigationHeaderMotionLayout(context: Context?, attrs: AttributeSet?) : MotionLayout(context, attrs) {

    private val motionScene by lazy {
        val field = MotionLayout::class.java.getDeclaredField("mScene")
        field.isAccessible = true
        val motionScene = field.get(this) as MotionScene
        motionScene
    }

    private var headerList = mutableListOf<HeaderView>()
    private var cSetList = mutableListOf<ConstraintSet>()
    private var cSetIdList = mutableListOf<Int>()
    private var isNavigationHeaderCollapsed: Boolean = false
    private var contentList: List<View>? = mutableListOf()
    private val colorEvaluator = ArgbEvaluator()
    private lateinit var activity: Activity
    private val window: Window by lazy { activity.window }
    var arrow: ImageView
    var expandListener: ((headerView: View) -> Unit)? = null
    var collapseListener: ((headerView: View) -> Unit)? = null

    init {
        val layoutInflater = LayoutInflater.from(this.context)
        arrow = layoutInflater.inflate(R.layout.navigation_arrow, this, false) as ImageView
        this.addView(arrow)
    }

    fun setOnExpandListener(listener: (headerView: View) -> Unit) {
        expandListener = listener
    }

    fun setOnCollapseListener(listener: (headerView: View) -> Unit) {
        collapseListener = listener
    }

    fun initNavigationHeader(
        activity: Activity,
        headerList: List<HeaderView>,
        contentList: List<View>? = null
    ) {
        this.activity = activity
        this.contentList = contentList
        buildMotionScene(headerList)
    }

    private fun addNavigationHeader(headerList: List<HeaderView>) {
        headerList.forEach {
            it.headerView.id = View.generateViewId()
            this.headerList.add(it)
            this.addView(it.headerView)
        }
    }

    private fun addArrow() {
        cSetList.forEach {
            createConstraint(
                it,
                arrow.id,
                ConstraintSet.WRAP_CONTENT,
                ConstraintSet.WRAP_CONTENT,
                headerList[0].headerView.id,
                ConstraintSet.UNSET,
                ConstraintSet.UNSET,
                ConstraintSet.UNSET,
                -1f,
                30f
            )
        }
    }

    private fun createConstraintSets() {
        val constraintSetExpend = ConstraintSet()
        val elevation = headerList.size.plus(6)
        for (i in 0 until headerList.size) {
            when (i) {
                0 -> {
                    createConstraint(
                        constraintSetExpend,
                        headerList[i].headerView.id,
                        0,
                        0,
                        ConstraintSet.UNSET,
                        ConstraintSet.PARENT_ID,
                        headerList[i + 1].headerView.id,
                        -1,
                        1.0f,
                        elevation.minus(i).toFloat()
                    )
                }
                in 1 until headerList.size - 1 -> {
                    createConstraint(
                        constraintSetExpend,
                        headerList[i].headerView.id,
                        0,
                        0,
                        ConstraintSet.UNSET,
                        ConstraintSet.UNSET,
                        headerList[i + 1].headerView.id,
                        headerList[i - 1].headerView.id,
                        1.0f,
                        elevation.minus(i).toFloat()
                    )
                }
                headerList.size - 1 -> {
                    createConstraint(
                        constraintSetExpend,
                        headerList[i].headerView.id,
                        0,
                        0,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.UNSET,
                        ConstraintSet.UNSET,
                        headerList[i - 1].headerView.id,
                        1.0f,
                        elevation.minus(i).toFloat()
                    )
                }
            }
        }
        cSetList.add(constraintSetExpend)

        for (k in 0 until headerList.size) {
            val constraintSetCollapse = ConstraintSet()

            for (i in 0 until headerList.size) {
                when (i) {
                    0 -> {
                        createConstraint(
                            constraintSetCollapse,
                            headerList[i].headerView.id,
                            0,
                            getHeight(k, i),
                            ConstraintSet.UNSET,
                            ConstraintSet.PARENT_ID,
                            ConstraintSet.UNSET,
                            ConstraintSet.UNSET,
                            -1.0f,
                            getElevation(k, i)
                        )
                    }
                    in 1 until headerList.size - 1 -> {
                        createConstraint(
                            constraintSetCollapse,
                            headerList[i].headerView.id,
                            0,
                            getHeight(k, i),
                            ConstraintSet.UNSET,
                            ConstraintSet.PARENT_ID,
                            ConstraintSet.UNSET,
                            ConstraintSet.UNSET,
                            -1.0f,
                            getElevation(k, i)
                        )
                    }
                    headerList.size - 1 -> {
                        createConstraint(
                            constraintSetCollapse,
                            headerList[i].headerView.id,
                            0,
                            getHeight(k, i),
                            ConstraintSet.UNSET,
                            ConstraintSet.PARENT_ID,
                            ConstraintSet.UNSET,
                            ConstraintSet.UNSET,
                            -1.0f,
                            getElevation(k, i)
                        )
                    }
                }
            }
            cSetList.add(constraintSetCollapse)
        }

        addArrow()
        cSetList.forEach {
            val constraintSetId = View.generateViewId()
            cSetIdList.add(constraintSetId)
            motionScene.setConstraintSet(constraintSetId, it)
        }
    }

    fun getHeight(k: Int, i: Int): Int {
        return if (k == i) 100 else 100
    }

    fun getElevation(k: Int, i: Int): Float {
        return if (k == i) 6f else 0f
    }

    private fun createConstraint(
        constraintSetExpend: ConstraintSet,
        viewId: Int,
        width: Int,
        height: Int,
        bottomToBottom: Int,
        topToTop: Int,
        bottomToTop: Int,
        topToBottom: Int,
        verticalWeight: Float,
        elevation: Float
    ) {
        val constrain = constraintSetExpend.getParameters(viewId)
        constrain.layout.mWidth = width
        constrain.layout.mHeight = height
        constrain.layout.bottomToBottom = bottomToBottom
        constrain.layout.topToTop = topToTop
        constrain.layout.bottomToTop = bottomToTop
        constrain.layout.topToBottom = topToBottom
        constrain.layout.verticalWeight = verticalWeight
        constrain.layout.startToStart = ConstraintSet.PARENT_ID
        constrain.layout.endToEnd = ConstraintSet.PARENT_ID
        constrain.layout.mApply = true

        val attribute = ConstraintAttribute("CardElevation", ConstraintAttribute.AttributeType.DIMENSION_TYPE)
        attribute.setFloatValue(elevation)
        constrain.mCustomConstraints["CardElevation"] = attribute

        constrain.motion.mApply = true
        constrain.transform.mApply = true
        constrain.propertySet.alpha = 1.0f
        constrain.propertySet.mApply = true
    }

    private fun createTransitions() {
        for (i in 0 until cSetIdList.size - 1) {
            val transition =
                MotionScene.Transition(View.generateViewId(), motionScene, cSetIdList[0], cSetIdList[i + 1])
            val transitionReturn =
                MotionScene.Transition(View.generateViewId(), motionScene, cSetIdList[i + 1], cSetIdList[0])
            transition.duration = 700
            transitionReturn.duration = 700
            motionScene.addTransition(transition)
            motionScene.addTransition(transitionReturn)
        }
        this.rebuildScene()
        this.invalidate()
    }

    fun buildMotionScene(headerList: List<HeaderView>) {
        addNavigationHeader(headerList)
        createConstraintSets()
        createTransitions()
        setHeaderListeners()
    }

    private fun setHeaderListeners() {
        for (i in 0 until headerList.size) {
            val (header, headerColor, statusBarColor, contentColor) = headerList[i]
            setHeaderListener(header, cSetIdList[i + 1], cSetIdList[0], statusBarColor, contentColor)
        }
    }

    private fun setHeaderListener(
        navigationHeader: View,
        outAnim: Int,
        inAnim: Int,
        statusBarColor: Int? = null,
        contentColor: Int? = null
    ) {
        navigationHeader.setOnClickListener {
            if (isNavigationHeaderCollapsed) {
                this.setTransition(outAnim, inAnim)
                expand(it)
            } else {
                this.setTransition(inAnim, outAnim)
                collapse(it, statusBarColor, contentColor)
            }
            this.transitionToEnd()
            isNavigationHeaderCollapsed = !isNavigationHeaderCollapsed
        }
    }

    private fun collapse(view: View, statusBarColor: Int?, contentColor: Int?) {
        arrow.isActivated = true
        statusBarColor?.let {
            animateStatusBar(window, "statusBarColor", window.statusBarColor, statusBarColor)
        }

        contentColor?.let {
            contentList?.let {
                it.forEach { view ->
                    val colorDrawable = view.background as ColorDrawable
                    animateStatusBar(view, "backgroundColor", colorDrawable.color, contentColor)
                }
            }
        }
        collapseListener?.let { it(view) }
    }

    private fun expand(view: View) {
        arrow.isActivated = false
        headerList[0].statusBarColor?.let {
            animateStatusBar(window, "statusBarColor", window.statusBarColor, it)
        }
        expandListener?.let { it(view) }
    }

    private fun animateStatusBar(target: Any, propertyName: String, fromColor: Int, color: Int) {
        val objectAnimator = ObjectAnimator.ofObject(target, propertyName, colorEvaluator, 0, 0)
        objectAnimator.setObjectValues(fromColor, ContextCompat.getColor(this.context, color))
        objectAnimator.duration = 700
        objectAnimator.start()
    }
}