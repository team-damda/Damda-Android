package com.etwoitwo.damda.feature.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Author: soo5717
 * Since: 2021-10-14
 * Description: 온보딩 뷰페이저 어댑터
**/
class OnBoardingPagerAdapter (fm: FragmentManager, lc: Lifecycle) : FragmentStateAdapter(fm, lc){
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = when(position) {
            0 -> OnBoarding1Fragment()
            1 -> OnBoarding2Fragment()
            2 -> OnBoarding3Fragment()
            else -> OnBoarding4Fragment()
        }
        return fragment
    }
}