package com.etwoitwo.damda.feature.main

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
import androidx.viewpager2.widget.ViewPager2
import com.etwoitwo.damda.model.network.RetrofitService
import com.etwoitwo.damda.databinding.FragmentMainStockBinding
import com.etwoitwo.damda.feature.common.PagerFragmentStateAdapter
import com.etwoitwo.damda.model.dataclass.MainStatusData
import com.etwoitwo.damda.model.network.SocketApplication
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat


class MainStockFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var mSocket: Socket
    private var _binding: FragmentMainStockBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var data: MainStatusData?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // * socket 연결
        mSocket = SocketApplication.get("main/status", "token=1")
        mSocket.connect()


        mSocket.on("reply_json", onMessageJson)
        _binding = FragmentMainStockBinding.inflate(inflater, container, false)
        loadData()

        // * 탭 레이아웃, 뷰 페이저 연결
        tabLayout = binding.tabMainMystock
        viewPager = binding.pagerMainStocklist

        val pagerAdapter = PagerFragmentStateAdapter(requireActivity())
        pagerAdapter.addFragment(MainContainFragment())
        pagerAdapter.addFragment(MainInterestFragment())
        viewPager.adapter = pagerAdapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int){

                super.onPageSelected(position)
//                Log.e("ViewPagerFragment", "Page ${position+1}")
            }
        })
        // tablayout attach
        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            val tabName = when(position){
                0 -> "보유종목"
                1 -> "관심종목"
                else -> "-"
            }
            tab.text = tabName
        }.attach()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mSocket.disconnect()
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
        val userid = 1
        // TODO 로그인 이미 했을 시 해당 토큰으로 보내기
        val call: Call<MainStatusData> = RetrofitService.service_ct_tab.requestMainStatus(UserId = userid)

        call.enqueue(object : Callback<MainStatusData> {
            override fun onFailure(call: Call<MainStatusData>, t: Throwable) {
                Log.d("loadData11 error", "from loaddata")
            }

            override fun onResponse(call: Call<MainStatusData>, response: Response<MainStatusData>) {
                Log.d("loadData11 response", "from loaddata")

                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let { _ ->
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