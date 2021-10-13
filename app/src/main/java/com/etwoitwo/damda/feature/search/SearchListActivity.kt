package com.etwoitwo.damda.feature.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.etwoitwo.damda.R
import com.etwoitwo.damda.databinding.ActivitySearchListBinding

class SearchListActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentFragment = supportFragmentManager.findFragmentById(binding.fragmentContainerViewSearch.id)

        if(currentFragment == null) {
            val fragment = SearchListFragment.newInstance("", "")
            supportFragmentManager
                .beginTransaction()
                .add(binding.fragmentContainerViewSearch.id, fragment)
                .commit()
        }
    }
}