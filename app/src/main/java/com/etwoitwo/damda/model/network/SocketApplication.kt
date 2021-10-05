package com.etwoitwo.damda.model.network

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

class SocketApplication {
    companion object {
        private lateinit var socket: Socket
        fun get(end:String, queryString: String): Socket {
            try {
                val options = IO.Options()
                options.query = queryString

                Log.d("connect socket111", "Like you")
                socket = IO.socket("http://192.168.0.39:5000/$end", options)
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
            return socket
        }
    }
}
