package com.etwoitwo.damda.feature.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.etwoitwo.damda.R
import com.etwoitwo.damda.databinding.FragmentOnBoarding4Binding
import com.etwoitwo.damda.feature.sign.SignInActivity

/**
 * Author: soo5717
 * Since: 2021-10-14
 * Description: 온보딩 4번째 페이지
**/
class OnBoarding4Fragment : Fragment() {
    private var _binding: FragmentOnBoarding4Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOnBoarding4Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: 2021-10-14 STACK 비우는 코드 추가
        binding.buttonStart.setOnClickListener {
            val intent = Intent(activity, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}