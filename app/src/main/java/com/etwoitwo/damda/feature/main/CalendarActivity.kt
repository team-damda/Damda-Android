package com.etwoitwo.damda.feature.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.etwoitwo.damda.R
import com.etwoitwo.damda.databinding.ActivityCalendarCopyBinding
import com.etwoitwo.damda.databinding.ActivityOnboardingBinding

/**
 * Author: soo5717
 * Since: 2021-10-14
 * Description: 온보딩 뷰페이저
 **/
class CalendarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalendarCopyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarCopyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}