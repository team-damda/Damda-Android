package com.etwoitwo.damda

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

class SocketApplication {
    companion object {
        private lateinit var socket: Socket
        fun get(): Socket {
            try {
                val options = IO.Options()
                options.query = "token=1"

                Log.d("connect socket111", "Like you")
                socket = IO.socket("http://192.168.0.39:5000/main/status", options)
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
            return socket
        }
    }
}
