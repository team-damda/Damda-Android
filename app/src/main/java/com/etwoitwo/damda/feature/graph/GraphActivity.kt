package com.etwoitwo.damda.feature.graph

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.etwoitwo.damda.R
import com.etwoitwo.damda.databinding.ActivityDetailInfoBinding


private lateinit var binding: ActivityDetailInfoBinding

class GraphActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFragment()
        binding.buttonOneDay.setOnClickListener{

            setFragment()
            Log.d("tag", "setOnClickListener")
        }

        binding.buttonOneWeek.setOnClickListener{
            setFragment()
        }

        binding.textViewDivision.setText("코스피") // 서버 연결
        binding.textViewStockCode.setText("005930") // 서버 연결
        binding.textViewStockName.setText("삼성전자") // 서버 연결
        binding.textViewStockMoney.setText("15000")

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
    }

    private fun setFragment(){
        val transaction = supportFragmentManager.beginTransaction()
            .add(R.id.flame_layout_graph_shape, ChartFragment1())
        transaction.commit()

    }
    private fun setFragmentOneWeek(){
        val transaction = supportFragmentManager.beginTransaction()
            .add(R.id.flame_layout_graph_shape, ChartFragment2())
        transaction.commit()
    }

}