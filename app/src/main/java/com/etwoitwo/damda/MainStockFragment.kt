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
import com.etwoitwo.damda.databinding.FragmentMainStockBinding
import java.text.DecimalFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [MainStock.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainStockFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var mSocket: Socket
    private var _binding: FragmentMainStockBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mSocket = SocketApplication.get()
        mSocket.connect()

        mSocket.on("reply", onMessage)
        mSocket.on("reply_json", onMessageJson)
        _binding = FragmentMainStockBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mSocket.disconnect()
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
            activity?.runOnUiThread(Runnable {
                // 에러 해결: Only the original thread that created a view hierarchy can touch its views.
                kotlin.run {
                    binding.txtviewMainNickname.setText(data.getString("nickname"))
                    val tDecUp = DecimalFormat("#,###")
                    val totAssetMoney = data.getString("deposit").toInt() + data.getString("containStockAsset").toInt()
                    val totAssetMoneyString = tDecUp.format(totAssetMoney) + "원"
                    binding.txtviewMainTotassetmoney.setText(totAssetMoneyString)
                    val history = data.getString("history").toString()
                    val historyString = "주식초보 $history 일차"
                    binding.txtviewMainMystatus.setText(historyString)
                }
            })


        } catch (e: JSONException){
            e.printStackTrace()
        }
    }


}