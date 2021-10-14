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
import com.etwoitwo.damda.feature.search.SearchListActivity


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
            setFragmentOneWeek()
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
            .replace(R.id.flame_layout_graph_shape, ChartFragment1())
            .addToBackStack(null)
        transaction.commit()

    }
    private fun setFragmentOneWeek(){
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.flame_layout_graph_shape, ChartFragment2())
            .addToBackStack(null)
        transaction.commit()
    }

}