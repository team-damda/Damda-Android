package com.etwoitwo.damda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONException
import org.json.JSONObject
import android.util.Log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [MainStock.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainStockFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var mSocket: Socket
//    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        mSocket = SocketApplication.get()
//        mSocket.connect()
//
//        mSocket.on("reply", onMessage)
//        mSocket.on("reply_json", onMessageJson)
        return inflater.inflate(R.layout.fragment_main_stock, container, false)
    }

    var onMessage = Emitter.Listener {
        // 서버에서 string 형식으로 보내는 경우
        Log.d("on message11", "new message from server")
        Log.d("server message11", it[0].toString())
    }

    var onMessageJson = Emitter.Listener {
        // 서버애서 json 형식으로 보내는 경우
        try {
            val jsonObj: JSONObject = it[0] as JSONObject
            val data: JSONObject = jsonObj.getJSONObject("data")
            Log.d("on socket11 nickname", data.getString("nickname"))
            Log.d("on socket11 deposit", data.getString("deposit"))
            Log.d("on socket11 history", data.getString("history"))
            Log.d("on socket11 asset", data.getString("containStockAsset"))

        } catch (e: JSONException){
            e.printStackTrace()
        }
    }
}