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
import com.etwoitwo.damda.feature.common.CommonHoldingFragment
import com.etwoitwo.damda.feature.common.PagerFragmentStateAdapter
import com.etwoitwo.damda.model.dataclass.CommonStatusData
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

    private lateinit var statSocket: Socket
    private lateinit var intSocket: Socket
    private lateinit var containSocket: Socket

    private var _binding: FragmentMainStockBinding? = null
    private val binding get() = _binding!!

    var data: CommonStatusData?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /* 라이프사이클 가장 첫번째로 실행됨 */
        // * socket 연결
        statSocket = SocketApplication.get("common/status", "token=1")
        intSocket = SocketApplication.get("main/interestStocks", "token=1")
        containSocket = SocketApplication.get("common/containStocks", "token=1")

        // * status socket 연결
        statSocket.connect()

        statSocket.on("reply_json", onMessageJson)

        // * 뷰 바인딩 적용
        _binding = FragmentMainStockBinding.inflate(inflater, container, false)

        // * rest api로 초기 데이터 받아오기
        loadData()

        // * 탭 레이아웃, 뷰 페이저 연결
        tabLayout = binding.tabMainMystock
        viewPager = binding.pagerMainStocklist

        val pagerAdapter = PagerFragmentStateAdapter(requireActivity())
        pagerAdapter.addFragment(CommonHoldingFragment(containSocket))
        pagerAdapter.addFragment(MainInterestFragment(intSocket))
        viewPager.adapter = pagerAdapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
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
        /* 라이프사이클 마지막: 뷰 파괴될 때 */
        super.onDestroyView()
        _binding = null
        statSocket.disconnect()
        intSocket.disconnect()
        containSocket.disconnect()
    }


    private var onMessageJson = Emitter.Listener {
        /* [소켓] 전송 받는 이벤트 리스너(서버 -> 안드) */
        try {
            val jsonObj: JSONObject = it[0] as JSONObject
            val data: JSONObject = jsonObj.getJSONObject("data")
            Log.d("on socket11 nickname", data.getString("nickname"))
            activity?.runOnUiThread {
                // 에러 해결: Only the original thread that created a view hierarchy can touch its views.
                kotlin.run {
                    binding.txtviewMainNickname.text = data.getString("nickname")
                    val tDecUp = DecimalFormat("#,###")
                    val totAssetMoney =
                        data.getString("deposit").toInt() + data.getString("containStockAsset")
                            .toInt()
                    val totAssetMoneyString = tDecUp.format(totAssetMoney) + "원"
                    binding.txtviewMainTotassetmoney.text = totAssetMoneyString
                    val history = data.getString("history")
                    val historyString = "주식초보 $history 일차"
                    binding.txtviewMainMystatus.text = historyString
                }
            }
        } catch (e: JSONException){
            e.printStackTrace()
        }
    }
    private fun loadData(){
        /* [REST] 초기 데이터 받아 와서 화면에 설정하기 */
        val userid = 1
        // TODO 로그인 이미 했을 시 해당 토큰으로 보내기
        val call: Call<CommonStatusData> = RetrofitService.service_ct_tab.requestCommonStatus(UserId = userid)

        call.enqueue(object : Callback<CommonStatusData> {
            override fun onFailure(call: Call<CommonStatusData>, t: Throwable) {
                Log.d("loadData11 error", "from loaddata")
            }

            override fun onResponse(call: Call<CommonStatusData>, response: Response<CommonStatusData>) {
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
        /* [REST] 초기 데이터 받아 올때 에러 터지는 경우 호출됨 */
        val e = error ?: return
        val ob = JSONObject(e.string())
        Toast.makeText(context, "네트워크 에러", Toast.LENGTH_SHORT).show()
        Log.d("loadData11 error", "$ob")
        binding.txtviewMainNickname.text = "-"
        binding.txtviewMainTotassetmoney.text = "-원"
        binding.txtviewMainMystatus.text = "-"
    }

}