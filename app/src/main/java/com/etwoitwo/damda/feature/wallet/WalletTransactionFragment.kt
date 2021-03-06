package com.etwoitwo.damda.feature.wallet

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.etwoitwo.damda.R
import com.etwoitwo.damda.databinding.FragmentWalletTransactionBinding
import com.etwoitwo.damda.feature.common.StockListAdapter
import com.etwoitwo.damda.model.dataclass.StockData
import com.etwoitwo.damda.model.network.RetrofitService
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WalletTransactionFragment : Fragment() {
    private var _binding: FragmentWalletTransactionBinding? = null
    private val binding get() = _binding!!
    private var data: StockData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //* 뷰 바인딩
        _binding = FragmentWalletTransactionBinding.inflate(inflater, container, false)

        //* spinner 설정하기
        val spinner = binding.spinnerWalletFilter
        val monthSpinnerArray:Array<String> = arrayOf("21/5월", "20/11월")
        val adapter = ArrayAdapter(requireContext(), R.layout.layout_wallet_spinneritem, monthSpinnerArray)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.d("spinner11 onItemClicked", "${spinner.getItemAtPosition(position)}")
            }
        }

        //* 라디오 버튼에 onclicklistener 설정해서 골라진 것 저장하기
        binding.btnWalletRadiogroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.btn_wallet_filterall -> {
                    Log.d("라디오 그룹", "filter all")
                    data?.data?.let{
                        setAdapter(it)
                    }
                }
                R.id.btn_wallet_filterbuy -> {
                    Log.d("라디오 그룹", "filter buy")
                    val buyStockList = data?.data?.filter {
                        it.viewType == 0 && it.transType == "매수" || it.viewType == 1 && (it.transDayType == 0 || it.transDayType == 2)
                    }
//                    Log.d("라디오 그룹", buyStockList.toString())
                    setAdapter(buyStockList as ArrayList<StockData.Data>)
                }
                R.id.btn_wallet_filtersell -> {
                    Log.d("라디오 그룹", "filter sell")
                    val sellStockList = data?.data?.filter {
                        it.viewType == 0 && it.transType == "매도" || it.viewType == 1 && (it.transDayType == 1 || it.transDayType == 2)
                    }
//                    Log.d("라디오 그룹", sellStockList.toString())
                    setAdapter(sellStockList as ArrayList<StockData.Data>)
                }
            }
        }

        //* [REST] wallet/transactions
        loadData()
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setAdapter(stockList: ArrayList<StockData.Data>){
        val stockListAdapter = StockListAdapter(stockList)
        val rvStock = requireView().findViewById<RecyclerView>(R.id.rcview_wallet_stocklist)
        rvStock.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvStock.adapter = stockListAdapter
        stockListAdapter.notifyDataSetChanged()
    }

    private fun replaceToEmptyFragment(){
        // TODO add기 때문에 기존 뷰가 그대로 보인다는 문제점 해결하기
        val fragmentManager = childFragmentManager
        val fragTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        fragTransaction.replace(R.id.frame_wallet_transaction_content, WalletTransactionNoneFragment())
        fragTransaction.commit()
    }

    private fun loadData(){
        // call back 등록해서 통신 요청
        val userid = 1
        // TODO 로그인 이미 했을 시 해당 토큰으로 보내기
        val call: Call<StockData> = RetrofitService.service_ct_tab.requestWalletTransactions(UserId = userid)

        call.enqueue(object : Callback<StockData> {
            override fun onFailure(call: Call<StockData>, t: Throwable) {
                Log.d("wallet loadData11 error", "from load data transactions")
                replaceToEmptyFragment()
            }

            override fun onResponse(call: Call<StockData>, response: Response<StockData>) {
                Log.d("wallet loadData11 succ", "from load data transactions")

                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let { _ ->
                        data = response.body()
//                        Log.d("wallet loadData11", data.toString())
                        data?.data?.let { it1 ->
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
        Log.d("wallet loadData11 error", "$ob")
        replaceToEmptyFragment()
    }
}
