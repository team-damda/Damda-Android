package com.etwoitwo.damda.model.network

import android.util.Log
import com.etwoitwo.damda.BuildConfig
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

class SocketApplication {
    companion object {
        private lateinit var socket: Socket
        fun get(end:String, queryString: String): Socket {
            try {
                val options = IO.Options()
                val baseUrl = BuildConfig.SERVER_API_KEY

                options.query = queryString

                Log.d("connect socket111", "$baseUrl$end")

                socket = IO.socket("$baseUrl$end", options)
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
            return socket
        }
    }
}
