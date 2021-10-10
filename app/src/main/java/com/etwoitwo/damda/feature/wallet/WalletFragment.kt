package com.etwoitwo.damda.feature.wallet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.etwoitwo.damda.R
import com.etwoitwo.damda.databinding.FragmentMainStockBinding
import com.etwoitwo.damda.databinding.FragmentWalletBinding
import com.etwoitwo.damda.feature.common.CommonHoldingFragment
import com.etwoitwo.damda.feature.common.PagerFragmentStateAdapter
import com.etwoitwo.damda.feature.main.MainInterestFragment
import com.etwoitwo.damda.model.dataclass.CommonStatusData
import com.etwoitwo.damda.model.network.RetrofitService
import com.etwoitwo.damda.model.network.SocketApplication
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import io.socket.client.Socket
import io.socket.emitter.Emitter
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat


class WalletFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    private lateinit var statSocket: Socket
    private lateinit var containSocket: Socket

    private var _binding: FragmentWalletBinding? = null
    private val binding get() = _binding!!

    var data: CommonStatusData?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // * socket 연결
        statSocket = SocketApplication.get("common/status", "token=1")
        containSocket = SocketApplication.get("common/containStocks", "token=1")
        statSocket.connect()

        statSocket.on("reply_json", onMessageJson)

        // * rest api로 초기 화면 진입 시 데이터 받아 오기
        loadData()

        // 뷰 바인딩
        _binding = FragmentWalletBinding.inflate(inflater, container, false)

        // * 탭 레이아웃, 뷰 페이저 연결
        tabLayout = binding.tabWalletMystock
        viewPager = binding.pagerWalletStocklist

        val pagerAdapter = PagerFragmentStateAdapter(requireActivity())
        pagerAdapter.addFragment(CommonHoldingFragment(containSocket))
        pagerAdapter.addFragment(WalletTransactionFragment())
        viewPager.adapter = pagerAdapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int){
                super.onPageSelected(position)
            }
        })

        // tablayout attach
        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            val tabName = when(position){
                0 -> "보유종목"
                1 -> "거래내역"
                else -> "-"
            }
            tab.text = tabName
        }.attach()

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        statSocket.disconnect()
        containSocket.disconnect()
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
                    // 주식 금액 업데이트
                    val tDecUp = DecimalFormat("#,###")
                    val stockAssetMoney = data.getString("containStockAsset").toInt()
                    val stockAssetMoneyString = tDecUp.format(stockAssetMoney) + "원"
                    binding.txtviewWalletMystockassetmoney.text = stockAssetMoneyString

                    // 예수금 금액 업데이트
                    val depositMoney = data.getString("deposit").toInt()
                    val depositMoneyString = tDecUp.format(depositMoney) + "원"
                    binding.txtviewWalletMydepositassetmoney.text = depositMoneyString

                    // 총 자산 업데이트
                    val totAssetMoney = stockAssetMoney + depositMoney
                    val totAssetMoneyString = tDecUp.format(totAssetMoney) + "원"
                    binding.txtviewWalletTotassetmoney.text = totAssetMoneyString
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
        val call: Call<CommonStatusData> = RetrofitService.service_ct_tab.requestCommonStatus(UserId = userid)

        call.enqueue(object : Callback<CommonStatusData> {
            override fun onFailure(call: Call<CommonStatusData>, t: Throwable) {
                Log.d("wallet loadData11 error", "from loaddata")
            }

            override fun onResponse(call: Call<CommonStatusData>, response: Response<CommonStatusData>) {
                Log.d("wallet loadData11", "from loaddata")

                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let { _ ->
                        data = response.body()
                        Log.d("wallet loadData11", data.toString())
                        data?.data?.let { it1 ->

                            // 주식 금액 업데이트
                            val tDecUp = DecimalFormat("#,###")
                            val stockAssetMoney = it1.containStockAsset.toInt()
                            val stockAssetMoneyString = tDecUp.format(stockAssetMoney) + "원"
                            binding.txtviewWalletMystockassetmoney.text = stockAssetMoneyString

                            // 예수금 금액 업데이트
                            val depositMoney = it1.deposit.toInt()
                            val depositMoneyString = tDecUp.format(depositMoney) + "원"
                            binding.txtviewWalletMydepositassetmoney.text = depositMoneyString

                            // 총 자산 업데이트
                            val totAssetMoney = stockAssetMoney + depositMoney
                            val totAssetMoneyString = tDecUp.format(totAssetMoney) + "원"
                            binding.txtviewWalletTotassetmoney.text = totAssetMoneyString
                        }
                    }?: showError(response.errorBody())

            }
        })
    }
    private fun showError(error: ResponseBody?){
        val e = error ?: return
        val ob = JSONObject(e.string())
        Toast.makeText(context, "네트워크 에러", Toast.LENGTH_SHORT).show()
        Log.d("wallet loadData11 error", "$ob")
        binding.txtviewWalletMystockassetmoney.text = "-원"
        binding.txtviewWalletMydepositassetmoney.text = "-원"
    }
}