package com.artf.navigationheader

import android.content.res.Resources

fun Float.toPx(): Float = (this * Resources.getSystem().displayMetrics.density)
fun Float.toDp(): Float = (this / Resources.getSystem().displayMetrics.density)