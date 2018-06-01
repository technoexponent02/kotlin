package com.technoexponent.sampleandroidapp.Constant

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication

import java.net.URISyntaxException

import io.socket.client.IO
import io.socket.client.Socket

/**
 * Created by mobile08 on 3/12/2018.
 */

class SocketApplication : MultiDexApplication() {

    var socket: Socket? = null
        private set

    init {
        try {
            socket = IO.socket("http://www.slandaise.com:55916")
        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
        }

    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }


}
