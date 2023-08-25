package com.example.first_mgr

import okhttp3.OkHttpClient

// Create an OkHttpClient with the logging interceptor
val httpClient = OkHttpClient.Builder()
    .addInterceptor(LoggingInterceptor())
    .build()
