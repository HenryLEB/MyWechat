package com.example.secondexperiment.utils

interface HttpCallbackListener {
    fun onFinish(response: String)
    fun onError(e: Exception)
}