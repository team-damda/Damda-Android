package com.etwoitwo.damda.feature.wallet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.etwoitwo.damda.R
import com.etwoitwo.damda.databinding.FragmentWalletTransactionBinding

/* [설계]

<wallet/transactions>
거래내역이 있던 월 리스트로 서버에서 보내 주기
받은 거래-월 데이터 monthSpinnerArray 에 집어넣기 (StringArray임)

받은 거래 내역 리스트에서 버튼을 클릭하면 매수/매도 클릭하여 보여줌.
이때 child fragment 교체해주기(데이터 있으면 -> wallet_transaction_list로, 데이터 없으면 -> wallet_transaction_none으로)

*/

class WalletTransactionFragment : Fragment() {
    private var _binding: FragmentWalletTransactionBinding? = null
    private val binding get() = _binding!!

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
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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

        return binding.root
    }


}