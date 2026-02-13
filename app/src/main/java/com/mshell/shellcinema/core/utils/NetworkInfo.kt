package com.mshell.shellcinema.core.utils

import com.mshell.shellcinema.BuildConfig


object NetworkInfo {
    val BASE_URL by lazy { BuildConfig.BASE_URL }
    val BASE_URL_IMAGE by lazy { BuildConfig.BASE_URL_IMAGE }
    val API_KEY by lazy { BuildConfig.API_KEY }
}