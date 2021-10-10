package com.etwoitwo.damda.feature.common

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.etwoitwo.damda.R
import com.etwoitwo.damda.feature.main.MainInterestNoneFragment
import com.etwoitwo.damda.model.dataclass.StockData
import com.etwoitwo.damda.model.network.RetrofitService
import com.etwoitwo.damda.model.network.SocketApplication
import io.socket.client.Socket
import io.socket.emitter.Emitter
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CommonHoldingFragment(var containSocket: Socket) : Fragment() {
    // TODO common 패키지로 옮기기

    var data: StockData?= null
//    private lateinit var isDataLengthZero: Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        containSocket.connect()

        containSocket.on("reply_json", onMessageJson)
        loadData()
        return inflater.inflate(R.layout.fragment_stock_list, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    var onMessageJson = Emitter.Listener {
        // 서버애서 json 형식으로 보내는 경우
        try {
            val jsonObj: JSONObject = it[0] as JSONObject
            val jsonarray_data: JSONArray = jsonObj.getJSONArray("data")
            Log.d("common holding socket", jsonarray_data.toString())
            // json array 형식 -> StockData.Data array 형식으로 변환해주기

            activity?.runOnUiThread(Runnable {
                // 에러 해결: Only the original thread that created a view hierarchy can touch its views.
                kotlin.run {

                    var dataset = arrayListOf<StockData.Data>()
                    for (i in 0 until jsonarray_data.length()){

                        val tempJson: JSONObject = jsonarray_data.getJSONObject(i)
                        val tempData: StockData.Data = StockData.Data(
                            marketType = tempJson.getString("marketType"),
                            stockId = tempJson.getString("stockId"),
                            stockName = tempJson.getString("stockName"),
                            currentPrice = tempJson.getInt("currentPrice"),
                            totCnt = tempJson.getInt("totCnt"),
                            totProfitLoss = tempJson.getInt("totProfitLoss"),
                            totProfitLossRate = tempJson.getDouble("totProfitLossRate"),
                        )
                        dataset.add(tempData)
                    }

                    if (dataset.size > 0){
                        Log.d("socket main interest", "dataset.size > 0")
                        setAdapter(dataset)
                    } else {
                        Log.d("socket main interest", "replaceToEmptyFragment")
                        setAdapter(dataset)
                        replaceToEmptyFragment()
                    }
                }
            })

        } catch (e: JSONException){
            e.printStackTrace()
        }
    }


    private fun setAdapter(stockList: ArrayList<StockData.Data>){
        val stockListAdapter = StockListAdapter(stockList)
        val rvStock = requireView().findViewById<RecyclerView>(R.id.rcview_stocklist)
        rvStock.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvStock.adapter = stockListAdapter
        stockListAdapter.notifyDataSetChanged()
    }

    private fun replaceToEmptyFragment(){
        // TODO add기 때문에 기존 뷰가 그대로 보인다는 문제점 해결하기
        val fragmentManager = childFragmentManager
        val fragTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragTransaction.add(R.id.layout_stocklist_wrapper, CommonHoldingNoneFragment())
        fragTransaction.commit()
    }

    private fun loadData(){
        // call back 등록해서 통신 요청
        val userid = 1
        // TODO 로그인 이미 했을 시 해당 토큰으로 보내기
        val call: Call<StockData> = RetrofitService.service_ct_tab.requestCommonHolding(UserId=userid)
//        val call: Call<StockData> = RetrofitService.service_ct_tab.requestCommonHolding()

        call.enqueue(object : Callback<StockData> {
            override fun onFailure(call: Call<StockData>, t: Throwable) {
                Log.d("common holding - ", "loadData11 error")
                Toast.makeText(context, "네트워크 에러", Toast.LENGTH_SHORT).show()
                replaceToEmptyFragment()
            }

            override fun onResponse(call: Call<StockData>, response: Response<StockData>) {
                Log.d("common holding ", "- loadData11 response")

                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let { _ ->
                        Log.d("common holding", "여기 1")
                        data = response.body()
                        Log.d("common holding", "여기 2")
                        Log.d("loadData11 responsedata", data?.data.toString())
                        data?.data?.let { it1 ->
                            Log.d("common holding", "여기 3")
                            if (it1.size > 0){
                                setAdapter(it1)
                            } else {
                                replaceToEmptyFragment()
                            }
                        }
                    }?: showError(response.errorBody())

            }
        })

    }
    private fun showError(error: ResponseBody?){
        val e = error ?: return
        val ob = JSONObject(e.string())
        Toast.makeText(context, "네트워크 에러", Toast.LENGTH_SHORT).show()
        Log.d("common holding ", "$ob")
        replaceToEmptyFragment()
    }
}