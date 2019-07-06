package com.artf.navigationheader

import android.content.Context
import android.util.SparseArray
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.constraintlayout.widget.ConstraintSet

class NavigationHeaderMotionScene(motionLayout: MotionLayout?) : MotionScene(motionLayout){

    override fun getConstraintSet(context: Context, id: String): ConstraintSet? {
        val field = MotionScene::class.java.getDeclaredField("mConstraintSetMap")
        field.isAccessible = true
        val constraintSetMap = field.get(this)
        if(constraintSetMap is SparseArray<*>) {
            for (i in 0 until constraintSetMap.size()) {
                val key = constraintSetMap.keyAt(i)

                if (id.toInt() == key) {
                    return constraintSetMap.get(key) as ConstraintSet
                }
            }
        }
        return null
    }
}