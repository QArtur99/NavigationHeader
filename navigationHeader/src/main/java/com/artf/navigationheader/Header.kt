package com.artf.navigationheader

import android.view.View

public data class Header(
    val title: String,
    val headerColor: Int? = null,
    val statusBarColor: Int? = null,
    val contentColor: Int? = null
)

public data class HeaderView(
    val headerView: View,
    val headerColor: Int? = null,
    val statusBarColor: Int? = null,
    val contentColor: Int? = null
)