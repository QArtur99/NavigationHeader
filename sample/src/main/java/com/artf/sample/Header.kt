package com.artf.sample

import android.view.View

data class Header(
    val title: String,
    val headerColor: Int? = null,
    val statusBarColor: Int? = null,
    val contentColor: Int? = null
)

data class HeaderView(
    val headerView: View,
    val headerColor: Int? = null,
    val statusBarColor: Int? = null,
    val contentColor: Int? = null
)