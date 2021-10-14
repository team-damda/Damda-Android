package com.etwoitwo.damda.feature.graph

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.etwoitwo.damda.MainActivity
import com.etwoitwo.damda.databinding.DialogUtillBuyingBinding
import com.etwoitwo.damda.feature.wallet.WalletFragment
import com.etwoitwo.damda.model.dataclass.CommonStatusData
import com.etwoitwo.damda.model.dataclass.SuccessData
import com.etwoitwo.damda.model.network.RetrofitService
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class BuyingCustomDialog : DialogFragment() {
    private var _binding: DialogUtillBuyingBinding? = null
    private val binding get() = _binding!!

    var data: SuccessData?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogUtillBuyingBinding.inflate(inflater, container, false)
        val view = binding.root
        setCancelable(false);
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        binding.buttonCancel.setOnClickListener {
            dismiss()    // 대화상자를 닫는 함수
        }
        binding.buttonBuying.setOnClickListener {
            Log.d("button on click", "loaddata 전")
            loadData()
            val intent = Intent(context, MainActivity::class.java)
            Toast.makeText(context, "매수를 완료했습니다.", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadData(){
        /* [REST] 매수 버튼 눌렀을 때 POST 요청하기 */
        // TODO 로그인 이미 했을 시 해당 토큰으로 보내기
        val body = HashMap<String, String>()
        body["UserId"] = "1"
        body["stockId"] = "078930"
        body["curCnt"] = "20"
        body["curPrice"] = "45000"

        val call: Call<SuccessData> = RetrofitService.service_ct_tab.requestGraphBuying(body)

        call.enqueue(object : Callback<SuccessData> {
            override fun onFailure(call: Call<SuccessData>, t: Throwable) {
                Log.d("loadData11 error", "from loaddata")
            }

            override fun onResponse(call: Call<SuccessData>, response: Response<SuccessData>) {
                Log.d("buying loadData11 res", "from loaddata")

                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let { _ ->
                        data = response.body()
                        Log.d("buying loaddata11", data.toString());
                    }?: showError(response.errorBody())
            }


        })
    }

    private fun showError(error: ResponseBody?){
        /* [REST] 초기 데이터 받아 올때 에러 터지는 경우 호출됨 */
        val e = error ?: return
        val ob = JSONObject(e.string())
        Toast.makeText(context, "네트워크 에러", Toast.LENGTH_SHORT).show()
        Log.d("buying loadData11 error", "$ob")

    }
}
