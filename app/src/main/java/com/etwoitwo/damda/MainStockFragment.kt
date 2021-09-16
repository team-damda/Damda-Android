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
import android.widget.Toast
import com.etwoitwo.damda.databinding.FragmentMainStockBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat


class MainStockFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var mSocket: Socket
    private var _binding: FragmentMainStockBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var data: MainStatusData ?= null

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
        loadData()
        return binding.root
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
                    binding.txtviewMainNickname.text = data.getString("nickname")
                    val tDecUp = DecimalFormat("#,###")
                    val totAssetMoney = data.getString("deposit").toInt() + data.getString("containStockAsset").toInt()
                    val totAssetMoneyString = tDecUp.format(totAssetMoney) + "원"
                    binding.txtviewMainTotassetmoney.text = totAssetMoneyString
                    val history = data.getString("history")
                    val historyString = "주식초보 $history 일차"
                    binding.txtviewMainMystatus.text = historyString
                }
            })


        } catch (e: JSONException){
            e.printStackTrace()
        }
    }
    private fun loadData(){
        // call back 등록해서 통신 요청
        val userid = 3
        // TODO 로그인 이미 했을 시 해당 토큰으로 보내기
        val call: Call<MainStatusData> = RetrofitService.service_ct_tab.requestAllData(UserId = userid)

        call.enqueue(object : Callback<MainStatusData> {
            override fun onFailure(call: Call<MainStatusData>, t: Throwable) {
                Log.d("loadData11 error", "from loaddata")
            }

            override fun onResponse(call: Call<MainStatusData>, response: Response<MainStatusData>) {
                Log.d("loadData11 response", "from loaddata")

                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let { it ->
                        data = response.body()
                        Log.d("loadData11 responsedata", data.toString())
                        data?.data?.let { it1 ->
                            Log.d("loadData11 responsedata", it1.nickname)
                            binding.txtviewMainNickname.text = it1.nickname
                            val tDecUp = DecimalFormat("#,###")
                            val totAssetMoney = it1.containStockAsset.toInt() + it1.deposit.toInt()
                            val totAssetMoneyString = tDecUp.format(totAssetMoney) + "원"
                            binding.txtviewMainTotassetmoney.text = totAssetMoneyString
                            val history = it1.history
                            val historyString = "주식초보 $history 일차"
                            binding.txtviewMainMystatus.text = historyString
                        }
                    }?: showError(response.errorBody())

            }
        })
    }
    private fun showError(error: ResponseBody?){
        val e = error ?: return
        val ob = JSONObject(e.string())
        Toast.makeText(context, "네트워크 에러", Toast.LENGTH_SHORT).show()
        Log.d("loadData11 error", "$ob")
        binding.txtviewMainNickname.text = "-"
        binding.txtviewMainTotassetmoney.text = "-원"
        binding.txtviewMainMystatus.text = "-"
    }

}