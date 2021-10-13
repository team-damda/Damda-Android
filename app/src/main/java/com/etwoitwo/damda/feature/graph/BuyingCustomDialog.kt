package com.etwoitwo.damda.feature.graph

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.etwoitwo.damda.databinding.DialogUtillBuyingBinding
import com.etwoitwo.damda.feature.wallet.WalletFragment

class BuyingCustomDialog : DialogFragment() {
    private var _binding: DialogUtillBuyingBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogUtillBuyingBinding.inflate(inflater, container, false)
        val view = binding.root
        setCancelable(false);
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        binding.buttonCancel.setOnClickListener {
            dismiss()    // 대화상자를 닫는 함수
        }
        binding.buttonBuying.setOnClickListener {
            val intent = Intent(context, WalletFragment::class.java)
            Toast.makeText(context, "매수를 완료했습니다.", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
