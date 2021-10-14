package com.etwoitwo.damda.feature.search

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.etwoitwo.damda.databinding.ActivitySearchListBinding

class SearchListActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val category1 = intent.getStringExtra("category1")
        val category2 = intent.getStringExtra("category2")

        val currentFragment = supportFragmentManager.findFragmentById(binding.fragmentContainerViewSearch.id)

        val text = if(category1 == "저렴한 주식") {
            binding.editTextTitle.setText(category1)
            binding.editTextTitle.setSelection(category1.length)
            "저렴한 주식"
        } else if(category2 == "커피 한 잔") {
            binding.editTextTitle.setText(category2)
            binding.editTextTitle.setSelection(category2.length)
            "커피 한 잔"
        } else {
            ""
        }

        if(currentFragment == null) {
            val fragment = SearchListFragment.newInstance(text)
            supportFragmentManager
                .beginTransaction()
                .add(binding.fragmentContainerViewSearch.id, fragment)
                .commit()
        }

        binding.editTextTitle.setOnEditorActionListener { v, actionId, event ->
            hideKeyboard()
            var handled = false
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                sendTitle(v.text.toString())
                handled = true
            }
            handled
        }
    }

    private fun sendTitle(text: String) {
        // 검색어를 바탕으로 리사이클러뷰 변경
//        Toast.makeText(this, binding.editTextTitle.text, Toast.LENGTH_SHORT).show()

        val fragment = SearchListFragment.newInstance(text)
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainerViewSearch.id, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.editTextTitle.windowToken, 0)
    }
}