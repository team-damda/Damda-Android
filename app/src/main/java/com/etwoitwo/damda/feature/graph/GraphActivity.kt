package com.etwoitwo.damda.feature.graph

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.etwoitwo.damda.R
import com.etwoitwo.damda.databinding.ActivityDetailInfoBinding
import com.etwoitwo.damda.feature.search.SearchListActivity

class GraphActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFragment()
        binding.radioGroupDay.setOnCheckedChangeListener{ group, checkedId ->
            when(checkedId) {
                R.id.button_one_day -> {
                    Log.d("radio", "1일")
                }
                R.id.button_one_week -> {
                    // 일주일 그래프 계산
                }
                R.id.button_one_month -> {
                    // 한달 그래프 계산
                }
                R.id.button_three_month -> {
                    Log.d("radio", "3개")
                }
                R.id.button_one_year -> {
                    Log.d("radio", "1년")
                }
            }
        }

        binding.textViewDivision.setText("코스닥") // 서버 연결
        binding.textViewStockCode.setText("078930") // 서버 연결
        binding.textViewStockName.setText("GS") // 서버 연결
        binding.textViewStockMoney.setText("45,000")

        binding.textViewIncreaseMoney.setText("▲ 1,000") // 서버 연결
        binding.textViewIncreaseRate.setText("+3.3") // 서버 연결

        binding.buttonBuying.setOnClickListener{
            val buyingIntent = Intent(this, BuyingActivity::class.java)
            startActivity(buyingIntent)
        }
        binding.buttonSelling.setOnClickListener{
            val sellingIntent = Intent(this, SellingActivity::class.java)
            startActivity(sellingIntent)
        }
        binding.buttonBack.setOnClickListener {
            val Intent = Intent(this, SearchListActivity::class.java)
            startActivity(Intent)
        }
        var flag = 0
        binding.buttonHeart.setOnClickListener{
            if(flag == 1){
                binding.buttonHeart.setImageResource(R.drawable.ic_favorite_off_24_dp)
                flag = 0
            }
            else{
                binding.buttonHeart.setImageResource(R.drawable.ic_favorite_on_24_dp)
                flag = 1
            }
        }
    }

    private fun setFragment(){
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.flame_layout_graph_shape, ChartFragment())
            .addToBackStack(null)
        transaction.commit()

    }

}