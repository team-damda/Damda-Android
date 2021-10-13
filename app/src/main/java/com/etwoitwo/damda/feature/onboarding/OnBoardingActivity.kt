package com.etwoitwo.damda.feature.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.etwoitwo.damda.R
import com.etwoitwo.damda.databinding.ActivityOnboardingBinding

/**
 * Author: soo5717
 * Since: 2021-10-14
 * Description: 온보딩 뷰페이저
**/
class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding

    // TODO: 2021-10-14 인디케이터 구현 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setPagerAdapter()
    }

    private fun setPagerAdapter() {
        binding.viewPager.adapter = OnBoardingPagerAdapter(supportFragmentManager, lifecycle)
    }
}