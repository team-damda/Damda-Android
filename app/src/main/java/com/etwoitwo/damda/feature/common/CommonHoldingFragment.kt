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
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CommonHoldingFragment : Fragment() {
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
        loadData()
        return inflater.inflate(R.layout.fragment_stock_list, container, false)
    }

    private fun setAdapter(stockList: ArrayList<StockData.Data>){
        val stockListAdapter = StockListAdapter(stockList)
        val rvStock = requireView().findViewById<RecyclerView>(R.id.rcview_stocklist)
        rvStock.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvStock.adapter = stockListAdapter
        stockListAdapter.notifyDataSetChanged()
    }

    private fun replaceToEmptyFragment(){
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