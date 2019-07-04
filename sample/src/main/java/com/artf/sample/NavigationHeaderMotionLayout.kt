package com.artf.sample

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.constraintlayout.widget.ConstraintAttribute
import androidx.constraintlayout.widget.ConstraintSet

class NavigationHeaderMotionLayout(context: Context?, attrs: AttributeSet?) : MotionLayout(context, attrs) {

    //private val motionScene = MotionScene(this)
    private val motionScene by lazy {
        val field = MotionLayout::class.java.getDeclaredField("mScene")
        field.isAccessible = true
        val motionScene = field.get(this) as MotionScene
        motionScene
    }
    private var headerList = mutableListOf<View>()
    private var cSetList = mutableListOf<ConstraintSet>()
    private var cSetIdList = mutableListOf<Int>()
    private var transitionList = mutableListOf<MotionScene.Transition>()
    private var isNavigationHeaderCollapsed: Boolean = false

//    init {
//        val field = MotionLayout::class.java.getDeclaredField("mScene")
//        field.isAccessible = true
//        val constraintSetMap = field.get(this)
//    }

    fun addNavigationHeader(header: View) {
        header.id = View.generateViewId()
        headerList.add(header)
        this.addView(header)
    }

    private fun createConstraintSets() {
        val constraintSetExpend = ConstraintSet()
        constraintSetExpend.isForceId = false
        for (i in 0 until headerList.size) {
            val constrain = constraintSetExpend.getParameters(headerList[i].id)
            constrain.layout.mWidth = 0
            constrain.layout.widthMin = -1
            constrain.layout.widthMax = -1
            constrain.layout.mHeight = 100
            constrain.layout.heightMin = -1
            constrain.layout.heightMax = -1
            constrain.layout.topMargin = 0
            constrain.layout.verticalWeight = 0f
            constrain.layout.bottomToBottom = ConstraintSet.UNSET
            constrain.layout.topToTop = ConstraintSet.PARENT_ID
            constrain.layout.endToEnd = ConstraintSet.PARENT_ID
            constrain.layout.startToStart = ConstraintSet.PARENT_ID
            constrain.layout.mApply = true

            val attribute = ConstraintAttribute("CardElevation", ConstraintAttribute.AttributeType.DIMENSION_TYPE)
            attribute.setFloatValue(28.0f)
            constrain.mCustomConstraints["CardElevation"] = attribute

            constrain.motion.mApply = true
            constrain.transform.mApply = true
            constrain.propertySet.alpha = 1.0f
            constrain.propertySet.mApply = true
        }
        cSetList.add(constraintSetExpend)

        val constraintSetCollapse = ConstraintSet()
        constraintSetCollapse.isForceId = false
        for (i in 0 until headerList.size) {
            val constrain = constraintSetCollapse.getParameters(headerList[i].id)
            constrain.layout.mWidth = 0
            constrain.layout.widthMin = -1
            constrain.layout.widthMax = -1
            constrain.layout.mHeight = 200
            constrain.layout.heightMin = -1
            constrain.layout.heightMax = -1
            constrain.layout.topMargin = 0
            constrain.layout.verticalWeight = 0f
            constrain.layout.bottomToBottom = ConstraintSet.UNSET
            constrain.layout.topToTop = ConstraintSet.PARENT_ID
            constrain.layout.endToEnd = ConstraintSet.PARENT_ID
            constrain.layout.startToStart = ConstraintSet.PARENT_ID
            constrain.layout.mApply = true

            val attribute = ConstraintAttribute("CardElevation", ConstraintAttribute.AttributeType.DIMENSION_TYPE)
            attribute.setFloatValue(0.0f)
            constrain.mCustomConstraints["CardElevation"] = attribute

            constrain.motion.mApply = true
            constrain.transform.mApply = true
            constrain.propertySet.alpha = 1.0f
            constrain.propertySet.mApply = true
        }
        cSetList.add(constraintSetCollapse)

        cSetList.forEach {
            val constraintSetId = View.generateViewId()
            cSetIdList.add(constraintSetId)
            motionScene.setConstraintSet(constraintSetId, it)
        }
    }

    private fun createTransitions() {
        for (i in 0 until cSetIdList.size - 1) {
            val transition =
                MotionScene.Transition(View.generateViewId(), motionScene, cSetIdList[i], cSetIdList[i + 1])
            val transitionReturn =
                MotionScene.Transition(View.generateViewId(), motionScene, cSetIdList[i + 1], cSetIdList[i])
            transition.duration = 700
            transitionReturn.duration = 700
            motionScene.addTransition(transition)
            motionScene.addTransition(transitionReturn)
        }
        this.rebuildScene()
        this.invalidate()
    }

    fun buildMotionScene() {
        createConstraintSets()
        createTransitions()
        setHeaderListeners()
        //this.setScene(motionScene)
    }

    private fun setHeaderListeners(){
        for(i in 0 until headerList.size){
            setHeaderListener(headerList[i], cSetIdList[i+1], cSetIdList[0])
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
                //expand()
            } else {
                this.setTransition(inAnim, outAnim)
                //collapse(statusBarColor, contentColor)
            }
            this.transitionToEnd()
            isNavigationHeaderCollapsed = !isNavigationHeaderCollapsed
        }
    }
}

