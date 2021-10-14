package com.etwoitwo.damda.feature.search

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.etwoitwo.damda.databinding.ItemSearchListBinding
import com.etwoitwo.damda.feature.sign.SignUpActivity
import com.etwoitwo.damda.model.dataclass.Search
import java.text.DecimalFormat

class SearchListAdapter(private val dataList: ArrayList<Search>) :
    RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {
    var items: ArrayList<Search> = dataList

    inner class ViewHolder(private val binding: ItemSearchListBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        fun setItem(item: Search) {
            val dec = DecimalFormat("#,###")
            val currentPrice = "${dec.format(item.currentPrice)} 원"

            binding.textViewStockMarket.text = item.marketType
            binding.textViewStockCode.text = item.stockId
            binding.textViewStockName.text = item.stockName
            binding.textViewStockCurrentPrice.text = currentPrice
        }

        override fun onClick(v: View?) {
            if (v != null) {
                // TODO: 2021-10-14 종목 상세 페이지로 전환 
                val intent = Intent(v.context, SignUpActivity::class.java)
                v.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setItem(items[position])
    }

    override fun getItemCount(): Int = items.size
}